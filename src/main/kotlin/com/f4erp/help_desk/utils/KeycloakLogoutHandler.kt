package com.f4erp.help_desk.utils

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class KeycloakLogoutHandler : LogoutHandler {

    override fun logout(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, authentication: Authentication) {
        try {
            httpServletRequest.logout()
        } catch (e: ServletException) {
            e.printStackTrace()
        }
    }
}
