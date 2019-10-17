package com.f4erp.help_desk.restend

import com.f4erp.help_desk.repositories.UserRepository
import com.f4erp.help_desk.utils.validators.Authenticator
import com.google.firebase.auth.FirebaseAuth
import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import java.util.*

@RestController
@RequestMapping("/data/api/v1/onboard")
class Onboard(
        @field:Autowired private val runtimeService: RuntimeService,
        @field:Autowired private val firebaseAuth: FirebaseAuth,
        @field:Autowired private val userRepository: UserRepository
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)

    @PostMapping("/start")
    fun createNewClient(
            @RequestBody body: Map<String, Any>,
            @RequestHeader("Authorization") authorizationToken: String
    ): Map<String, Any> {
        val user = authenticator.checkIsAuthenticate(authorizationToken)
                ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN, "Unauthorized")
        val result = this.runtimeService
                .startProcessInstanceByKey("Onboard",
                        mapOf(
                                "Name" to body["name"] as String,
                                "DateOfBirth" to Date(body["dateOfBirth"] as Long),
                                "Address" to body["address"] as String,
                                "Description" to body["description"] as String,
                                "uid" to user.uid,
                                "Mobile" to body["mobile"] as String,
                                "Email" to user.email
                        )
                )
        return mapOf("data" to mapOf("processId" to result.processInstanceId))
    }

    @GetMapping("status")
    fun isUserInDb(
            @RequestHeader("Authorization") authorizationToken: String
    ): Any {
        val user = authenticator.checkIsAuthenticate(authorizationToken)
                ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN, "Unauthorized")

        val count = runtimeService.createProcessInstanceQuery()
                .activityIdIn("Task_1kw3l0n")
                .processDefinitionKey("Onboard")
                .variableValueEquals("uid", user.uid)
                .active().count()

        if (count == 1L) {
            return mapOf("data" to mapOf("status" to "pending"))
        }

        val userInfo = userRepository.findById(user.uid)
        if (userInfo.isEmpty) {
            return mapOf("data" to mapOf("status" to "not-registered"))
        }

        return mapOf("data" to mapOf("status" to "approved", userInfo to userInfo))
    }
}
