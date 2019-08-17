package com.f4erp.help_desk.auth

import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult
import org.camunda.bpm.engine.rest.security.auth.impl.ContainerBasedAuthenticationProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.util.StringUtils
import java.util.*
import javax.servlet.http.HttpServletRequest

class KeycloakAuthenticationProvider : ContainerBasedAuthenticationProvider() {

    override fun extractAuthenticatedUser(request: HttpServletRequest, engine: ProcessEngine?): AuthenticationResult {

        // Extract authentication details
        val authentication = SecurityContextHolder.getContext().authentication as OAuth2Authentication
                ?: return AuthenticationResult.unsuccessful()
        val userAuthentication = authentication.userAuthentication
        if (userAuthentication == null || userAuthentication.details == null) {
            return AuthenticationResult.unsuccessful()
        }

        // Extract user ID from Keycloak authentication result - which is part of the requested user info
        val userId = (userAuthentication.details as HashMap<String, String>).get("preferred_username")
        if (StringUtils.isEmpty(userId)) {
            return AuthenticationResult.unsuccessful()
        }

        // Authentication successful
        val authenticationResult = AuthenticationResult(userId, true)
        authenticationResult.groups = getUserGroups("$userId", engine!!)

        return authenticationResult
    }

    private fun getUserGroups(userId: String, engine: ProcessEngine): List<String> {
        val groupIds = ArrayList<String>()
        // query groups using KeycloakIdentityProvider plugin
        engine.identityService.createGroupQuery()
                .groupMember(userId)
                .list()
                .forEach { g -> groupIds.add(g.id) }

        return groupIds
    }

}
