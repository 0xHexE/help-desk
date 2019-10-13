package com.f4erp.help_desk.restend

import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/onboard")
class Onboard(@field:Autowired private val runtimeService: RuntimeService) {
    @PostMapping
    fun createNewClient(): String? {
        return this.runtimeService
                .startProcessInstanceByKey("onboard", mapOf("" to 1))
                .processInstanceId
    }
}
