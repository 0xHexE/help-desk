package com.f4erp.help_desk.restend

import com.f4erp.help_desk.entities.UserEntity
import com.f4erp.help_desk.repositories.UserRepository
import com.f4erp.help_desk.utils.validators.Authenticator
import com.google.firebase.auth.FirebaseAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
// api is already taken
@RequestMapping("/data/v1/clients")
class ClientsController(
        @field:Autowired private val userRepository: UserRepository,
        @field:Autowired private val firebaseAuth: FirebaseAuth
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)

    @GetMapping
    fun getClients(): Map<String, List<UserEntity>> {
        return mapOf(
            "data" to userRepository.findAll().toList()
        )
    }

    @PostMapping("/create")
    fun addClient(): Map<String, Int> {
        return mapOf("omkar" to 1)
    }
}
