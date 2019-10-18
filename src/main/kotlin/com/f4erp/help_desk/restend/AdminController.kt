package com.f4erp.help_desk.restend

import com.f4erp.help_desk.entities.UserDoctorAssignment
import com.f4erp.help_desk.repositories.UserDoctorAssignmentRepository
import com.f4erp.help_desk.repositories.UserRepository
import com.f4erp.help_desk.utils.validators.Authenticator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import java.nio.charset.Charset
import java.util.*
import javax.ws.rs.QueryParam


@RestController
@RequestMapping("/data/api/v1/admin")
class AdminController(
        @field:Autowired private val runtimeService: RuntimeService,
        @field:Autowired private val userRepository: UserRepository,
        @field:Autowired private val firebaseAuth: FirebaseAuth,
        @field:Autowired private val userDoctorAssignmentRepository: UserDoctorAssignmentRepository
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)

    // TODO: Secure this
    @PostMapping("/add-user")
    fun addDoctor(
            @RequestHeader("Authorization") encoding: String,
            @RequestBody body: Map<String, Any>
    ): Map<String, Map<String, String?>> {
        authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        val array = ByteArray(15) // length is bounded by 7
        Random().nextBytes(array)
        val userFirebase = UserRecord.CreateRequest()
                .setDisabled(false)
                .setDisplayName(body["name"] as String)
                .setEmail(body["email"] as String)
                .setEmailVerified(false)
                .setPassword(String(array, Charset.forName("UTF-8")))
                .setPhoneNumber("+91${body["mobile"] as String}")
        val data = firebaseAuth.createUser(userFirebase)
        val user = com.f4erp.help_desk.entities.UserEntity()
        user.address = body["address"] as String
        user.name = body["name"] as String
        user.dateOfBirth = Date(body["dateOfBirth"] as Long)
        user.uid = data.uid
        user.mobileNumber = body["mobile"] as String
        user.email = body["email"] as String
        user.userRole = body["type"] as String
        val newUser = userRepository.save(user)
        return mapOf("data" to mapOf("userId" to newUser.uid))
    }

    @PatchMapping("/associate-doctor/:client/:doctor")
    fun associateDoctor(
            @RequestHeader("Authorization") encoding: String,
            @RequestBody body: Map<String, Any>,
            @RequestParam("client") client: String,
            @RequestParam("doctor") doctor: String
    ): Map<String, Map<String, String>> {
        val data = userRepository.findById(client).get()
        val userDoctorAssignment = UserDoctorAssignment()
        userDoctorAssignment.client = data
        val savedDoctor = userRepository.findById(doctor)
        userDoctorAssignment.doctor = savedDoctor.get()
        userDoctorAssignmentRepository.save(userDoctorAssignment)
        return mapOf("data" to mapOf("client" to client, "doctor" to doctor))
    }

    @GetMapping("/users")
    fun getDoctors(
            @RequestHeader("Authorization") encoding: String,
            @QueryParam("type") type: String
    ): Map<String, List<com.f4erp.help_desk.entities.UserEntity>> {
        authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        // TODO: ADD INTO SQL
        print(type)
        val users = userRepository.findAll().filter { res -> res.userRole == type.split("=")[1] }
        return mapOf("data" to users)
    }
}
