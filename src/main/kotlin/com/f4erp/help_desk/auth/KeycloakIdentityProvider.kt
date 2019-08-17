package com.f4erp.help_desk.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.camunda.bpm.extension.keycloak.plugin.KeycloakIdentityProviderPlugin

@Component
@ConfigurationProperties(prefix = "plugin.identity.keycloak")
class KeycloakIdentityProvider : KeycloakIdentityProviderPlugin()
