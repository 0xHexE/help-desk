package com.f4erp.help_desk.restend

import com.f4erp.help_desk.entities.TreatmentTypeEntity
import com.f4erp.help_desk.repositories.TreatmentRepository
import com.f4erp.help_desk.utils.validators.Authenticator
import com.google.firebase.auth.FirebaseAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException

@RestController
@RequestMapping("/data/api/v1/treatment-type")
class TreatmentTypeController(
        private val treatmentRepository: TreatmentRepository,
        @field:Autowired private val firebaseAuth: FirebaseAuth
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)
    @PostMapping
    fun addDepartment(
            @RequestBody body: Map<String, String>,
            @RequestHeader("Authorization") encoding: String
    ): Map<String, Map<String, Long?>> {
        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        val department = TreatmentTypeEntity()
        department.name = body["name"] as String
        val returnData = treatmentRepository.save(department)
        return mapOf("data" to mapOf("id" to returnData.id))
    }

    @GetMapping
    fun getDepartment(
            @RequestHeader("Authorization") encoding: String
    ): MutableIterable<TreatmentTypeEntity> {
        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        return treatmentRepository.findAll()
    }
}
