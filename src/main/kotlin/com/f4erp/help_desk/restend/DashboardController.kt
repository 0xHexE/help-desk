package com.f4erp.help_desk.restend

import com.f4erp.help_desk.repositories.AppointmentsRepository
import org.camunda.bpm.engine.RuntimeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/data/api/v1/dashboard")
class DashboardController(
        val runtimeService: RuntimeService,
        val appointmentsRepository: AppointmentsRepository
) {
    @GetMapping
    @ResponseBody
    fun getDashboardContent(
            @RequestBody(required = true) body: RequestBody
    ): Map<String, Any> {
        return mapOf(
                "omkar" to ""
        )
    }
}