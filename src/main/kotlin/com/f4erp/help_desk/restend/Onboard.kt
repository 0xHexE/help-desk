package com.f4erp.help_desk.restend

import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/onboard")
class Onboard(@field:Autowired private val runtimeService: RuntimeService) {
    @PostMapping
    fun createNewClient(
            @RequestBody body: Map<String, Any>
    ): String? {
        return this.runtimeService
                .startProcessInstanceByKey("onboard",
                        mapOf(
                                "Name" to body["name"] as String,
                                "DateOfBirth" to Date(body["dateOfBirth"] as Long * 1000),
                                "Address" to body["address"],
                                "Doctor" to body["doctor"],
                                "Description" to body["description"]
                        )
                )
                .processInstanceId
    }
}
