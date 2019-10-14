package com.f4erp.help_desk

//import firebase.auth.EnableFirebaseSecurity
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.f4erp.help_desk.repositories"])
@Configuration
@EnableProcessApplication("HelpDesk")
open class HelpDeskApplication

fun main(args: Array<String>) {
    runApplication<HelpDeskApplication>(*args)
}
