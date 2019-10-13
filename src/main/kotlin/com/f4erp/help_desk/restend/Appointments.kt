package com.f4erp.help_desk.restend

import com.f4erp.help_desk.validation.NewAppointment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid


@RestController
@RequestMapping("/appointment")
class Appointments(@field:Autowired private val runtimeService: RuntimeService
) {
    @GetMapping("/pending")
    fun getPendingProcesses(): MutableList<ProcessInstance>? {
        return runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("appointment")
                .active()
                .list()
    }

    @GetMapping("/waiting-for-appointment")
    fun getWaitingAppointment(): Int {
        return runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("appointment")
                .activityIdIn("IntermediateThrowEvent_08koyuz")
                .list()
                .count()
    }

    @PostMapping
    fun createNewAppointment(
            @Valid newAppointment: NewAppointment,
            bindingResult: BindingResult
    ): String? {

        // TODO: GET ASSIGNEE FROM DB

        val variables = mapOf(
//                "Assignee" to newAppointment,
                "Date" to newAppointment.Date,
                "Description" to newAppointment.Description,
                "Issue" to newAppointment.Issue,
                "Time" to newAppointment.Time
        )

        return runtimeService.startProcessInstanceByKey("appointment", variables)
                .caseInstanceId
    }
}
