package com.f4erp.help_desk.restend

import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notification")
class NotificationRestController(
        @field:Autowired private val runtimeService: RuntimeService
) {
    @GetMapping
    fun getSelfTasks() {
    }
}
