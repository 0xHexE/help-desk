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
@RequestMapping("/f4erp/api/v1/onboard")
class Onboard(
        @field:Autowired private val runtimeService: RuntimeService,
        @field:Autowired private val firebaseAuth: FirebaseAuth,
        @field:Autowired private val userRepository: UserRepository
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)

    @PostMapping
    fun createNewClient(
            @RequestBody body: Map<String, Any>
    ): String? {
        return this.runtimeService
                .startProcessInstanceByKey("Onboard",
                        mapOf(
                                "Name" to body["name"] as String,
                                "DateOfBirth" to Date(body["dateOfBirth"] as Long * 1000),
                                "Address" to body["address"] as String,
                                "Description" to body["description"] as String
                        )
                )
                .processInstanceId
    }

    @GetMapping
    fun isUserInDb(
            @RequestHeader("Authorization") authorizationToken: String
    ): Any {
        val user = authenticator.checkIsAuthenticate(authorizationToken)
                ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN, "Unauthorized")
        val userInfo = userRepository.findById(user.uid)
        if (userInfo.isEmpty) {
            return mapOf("error" to "User is not registered", "statusCode" to 401)
        }
        return userInfo.get()
    }
}
