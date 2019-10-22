package com.f4erp.help_desk.restend

import com.f4erp.help_desk.entities.AppointmentEntity
import com.f4erp.help_desk.repositories.AppointmentsRepository
import com.f4erp.help_desk.repositories.UserDoctorAssignmentRepository
import com.f4erp.help_desk.repositories.UserRepository
import com.f4erp.help_desk.utils.validators.Authenticator
import com.google.firebase.auth.FirebaseAuth
import org.camunda.bpm.engine.FormService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.runtime.ActivityInstance
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
        @field:Autowired private val userDoctorAssignmentRepository: UserDoctorAssignmentRepository,
        @field:Autowired private val userRepository: UserRepository,
        @field:Autowired private val taskService: TaskService,
        @field:Autowired private val formService: FormService
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)

    @Throws(HttpClientErrorException::class)
    @GetMapping("/list")
    fun getAppointments(
            @RequestParam param: Map<String, String>,
            @RequestHeader("Authorization") encoding: String
    ): Map<String, List<AppointmentEntity>> {
        print(param)
        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        val data = userRepository.findById(user.uid).get()
        return if (data.userRole == "doctor") {
            mapOf("data" to this.appointmentsRepository.getAppointmentsInRange(Date(param["from"]?.toLong()!!), Date(param["to"]?.split("?")!![0].toLong())).filter { it.doctor?.uid === data.uid })
        } else {
            mapOf("data" to this.appointmentsRepository.getAppointmentsInRange(Date(param["from"]?.toLong()!!), Date(param["to"]?.split("?")!![0].toLong())))
        }
    }

    @GetMapping("/pending")
    fun getPendingProcesses(): MutableList<ProcessInstance>? {
        return runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("appointment")
                .active()
                .list()
    }

    @GetMapping("/info/{id}")
    fun getInfo(
            @PathVariable id: String,
            @RequestHeader("Authorization") encoding: String
    ): ActivityInstance? {
        authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        return runtimeService.getActivityInstance(id)
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
        val variables = mutableMapOf(
                "Assignee" to (data?.doctor?.uid),
                "Date" to Date(body["date"] as Long),
                "Description" to body["description"] as String,
                "Issue" to body["issue"] as String,
                "Time" to 12L,
                "client" to body["client"] as String,
                "ID" to UUID.randomUUID().toString()
        )

        if (body["treatment"] != null) {
            variables["TreatmentType"] = body["treatment"] as Long
        }

        if (body["department"] != null) {
            variables["Department"] = body["department"] as Long
        }

        return mapOf("data" to mapOf("id" to runtimeService.startProcessInstanceByKey("appointment", variables)
                .processInstanceId))
    }

    @GetMapping("/task/variables/{id}")
    fun getVariables(@PathVariable id: String): Map<String, MutableMap<String, Any>> {
        val returnValue: MutableMap<String, Any> = mutableMapOf()

        runtimeService.createVariableInstanceQuery()
                .activityInstanceIdIn(id)
                .list()
                .forEach { returnValue[it.name] = it.value }

        return mapOf("data" to returnValue)
    }

    @PostMapping("/task/complete/client-approve/{id}")
    fun completeAction(@PathVariable id: String, @RequestBody body: Map<String, Any>): Map<String, Map<String, Boolean>> {
        val result = taskService.createTaskQuery()
                .processInstanceId(id)
                .singleResult()

        formService.submitTaskForm(result.id, mapOf("IsClientAvailable" to body["isClientAvailable"], "ThenWhichDate" to "", "Note" to ""))

        return mapOf("data" to mapOf("success" to true))
    }

    @PostMapping("/task/complete/is-arrived/{id}")
    fun checkIsUser(@PathVariable id: String, @RequestBody body: Map<String, Any>): Map<String, Map<String, Boolean>> {
        val result = taskService.createTaskQuery()
                .processInstanceId(id)
                .singleResult()

        formService.submitTaskForm(result.id, mapOf("IsClientArrived" to body["isClientArrived"] as Boolean))
        return mapOf("data" to mapOf("success" to true))
    }
}
