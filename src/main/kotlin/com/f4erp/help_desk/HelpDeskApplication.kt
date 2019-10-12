package com.f4erp.help_desk

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@EnableProcessApplication("HelpDesk")
open class HelpDeskApplication

fun main(args: Array<String>) {
    runApplication<HelpDeskApplication>(*args)
}
