package com.f4erp.help_desk.restend

import com.f4erp.help_desk.entities.AppointmentEntity
import com.f4erp.help_desk.repositories.AppointmentsRepository
import com.f4erp.help_desk.repositories.UserDoctorAssignmentRepository
import com.f4erp.help_desk.utils.validators.Authenticator
import com.google.firebase.auth.FirebaseAuth
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import java.util.*


@RestController
@RequestMapping("/data/api/v1/appointment")
class Appointments(
        @field:Autowired private val runtimeService: RuntimeService,
        @field:Autowired private val appointmentsRepository: AppointmentsRepository,
        @field:Autowired private val firebaseAuth: FirebaseAuth,
        @field:Autowired private val userDoctorAssignmentRepository: UserDoctorAssignmentRepository
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)

    @Throws(HttpClientErrorException::class)
    @GetMapping("/get-appointments")
    fun getAppointments(
            @RequestHeader("Authorization") encoding: String
    ): Map<String, List<AppointmentEntity>> {
        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        val todayLastTime = Calendar.getInstance()
        todayLastTime.set(Calendar.HOUR_OF_DAY, 23)
        todayLastTime.set(Calendar.MINUTE, 59)
        return mapOf("data" to this.appointmentsRepository.getTodaysAppointments(today, todayLastTime))
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
            @RequestHeader("Authorization") encoding: String,
            @RequestBody body: Map<String, Any>
    ): Map<String, Map<String, String>> {
        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        val data = userDoctorAssignmentRepository.findAll().find { res -> res.client?.uid == (body["client"] as String) }
        print(data?.doctor?.uid)
        val variables = mutableMapOf(
                "Assignee" to (data?.doctor?.uid),
                "Date" to Date(body["date"] as Long),
                "Description" to body["description"] as String,
                "Issue" to body["issue"] as String,
                "Time" to 12L,
                "client" to body["client"] as String
        )

        return mapOf("data" to mapOf("id" to runtimeService.startProcessInstanceByKey("appointment", variables)
                .processInstanceId))
    }
}
