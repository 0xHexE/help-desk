package com.f4erp.help_desk.restend

import com.f4erp.help_desk.entities.AppointmentEntity
import com.f4erp.help_desk.repositories.AppointmentsRepository
import com.f4erp.help_desk.repositories.UserRepository
import com.f4erp.help_desk.utils.validators.Authenticator
import com.f4erp.help_desk.validation.NewAppointment
import com.google.firebase.auth.FirebaseAuth
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import javax.validation.Valid

@RestController
@RequestMapping("/appointment")
class Appointments(
        @field:Autowired private val runtimeService: RuntimeService,
        @field:Autowired private val appointmentsRepository: AppointmentsRepository,
        @field:Autowired private val firebaseAuth: FirebaseAuth
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)

    @Throws(HttpClientErrorException::class)
    @GetMapping("/get-appointments")
    fun getAppointments(
            @RequestHeader("Authorization") encoding: String
    ): MutableIterable<AppointmentEntity> {
        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        return this.appointmentsRepository.findAll()
    }

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
                "Assignee" to newAppointment,
                "Date" to newAppointment.Date,
                "Description" to newAppointment.Description,
                "Issue" to newAppointment.Issue,
                "Time" to newAppointment.Time
        )

        return runtimeService.startProcessInstanceByKey("appointment", variables)
                .caseInstanceId
    }
}
