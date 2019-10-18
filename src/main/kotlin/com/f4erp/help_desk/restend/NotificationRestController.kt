package com.f4erp.help_desk.restend

import com.f4erp.help_desk.repositories.UserRepository
import com.f4erp.help_desk.utils.validators.Authenticator
import com.google.firebase.auth.FirebaseAuth
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.VariableInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException

@RestController
@RequestMapping("/data/api/v1/notification")
class NotificationRestController(
        @field:Autowired private val runtimeService: RuntimeService,
        @field:Autowired private val firebaseAuth: FirebaseAuth,
        @field:Autowired private val userRepository: UserRepository
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)

    @GetMapping
    fun getSelfTasks(
            @RequestHeader("Authorization") encoding: String
    ): Map<String, MutableList<Any>> {
        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        val userInfo = userRepository.findById(user.uid).get()

        when (userInfo.userRole) {
            "client" -> {
                val processInstance = mutableListOf<Any>()
                val result1 = runtimeService.createProcessInstanceQuery()
                        .activityIdIn("Task_1gqwpwv")
                        .variableValueEquals("client", user.uid)
                        .list()
                        .map { res -> mapOf("notificationId" to res.processInstanceId, "type" to "waiting-for-client") }
                processInstance.addAll(result1)
                return mapOf("data" to processInstance)
            }
            "admin" -> {
                val processInstance = mutableListOf<Any>()
                val result1 = runtimeService.createProcessInstanceQuery()
                        .activityIdIn("Task_1kw3l0n")
                        .list()
                        .map { res -> mapOf("notificationId" to res.processInstanceId, "type" to "approve-new-user") }
                processInstance.addAll(result1)
                return mapOf("data" to processInstance)
            }
            "doctor" -> {
                val processInstance = mutableListOf<Any>()
                val result1 = runtimeService.createProcessInstanceQuery()
                        .activityIdIn("Task_0knnp4t")
                        .variableValueEquals("Assignee", user.uid)
                        .list()
                        .map { res -> mapOf("notificationId" to res.processInstanceId, "type" to "check-for-appointment") }
                processInstance.addAll(result1)
                return mapOf("data" to processInstance)
            }
            else -> {
                throw HttpClientErrorException(HttpStatus.BAD_REQUEST)
            }
        }
    }

    @GetMapping("/get-variables/:process")
    fun getVariables(
            @RequestHeader("Authorization") encoding: String,
            @RequestPart("process") process: String
    ): MutableList<VariableInstance>? {
        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        return runtimeService.createVariableInstanceQuery().activityInstanceIdIn(process)
                .list()
    }
}
