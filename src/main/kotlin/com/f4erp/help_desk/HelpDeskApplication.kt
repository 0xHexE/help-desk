package com.f4erp.help_desk

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableProcessApplication("HelpDesk")
open class HelpDeskApplication

fun main(args: Array<String>) {
    runApplication<HelpDeskApplication>(*args)
}
