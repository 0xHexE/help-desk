package com.f4erp.help_desk.auth

import com.f4erp.help_desk.utils.KeycloakLogoutHandler
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.context.request.RequestContextListener

import java.util.Collections

@Configuration
@EnableOAuth2Sso
open class WebAppSecurityConfig : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().ignoringAntMatchers("/api/**")
                .and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/app/**")
                .authenticated()
                .anyRequest()
                .permitAll().and()
                .logout()
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("http://keycloak/auth/realms/master/protocol/openid-connect/logout?redirect_uri=backToCamunda")
                .addLogoutHandler(KeycloakLogoutHandler())
                .clearAuthentication(true)
    }

    @Bean
    open fun requestContextListener(): RequestContextListener {
        return RequestContextListener()
    }
}
