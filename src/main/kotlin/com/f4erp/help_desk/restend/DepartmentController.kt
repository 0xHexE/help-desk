package com.f4erp.help_desk.restend

import com.f4erp.help_desk.entities.DepartmentEntity
import com.f4erp.help_desk.repositories.DepartmentRepository
import com.f4erp.help_desk.utils.validators.Authenticator
import com.google.firebase.auth.FirebaseAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException

@RestController
@RequestMapping("/data/api/v1/department")
class DepartmentController(
        private val departmentRepository: DepartmentRepository,
        @field:Autowired private val firebaseAuth: FirebaseAuth
) {
    private val authenticator: Authenticator = Authenticator(this.firebaseAuth)
    @PostMapping
    fun addDepartment(
            @RequestBody body: Map<String, String>,
            @RequestHeader("Authorization") encoding: String
    ): Map<String, Map<String, Long?>> {
        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        val department = DepartmentEntity()
        department.name = body["name"] as String
        val returnData = departmentRepository.save(department)
        return mapOf("data" to mapOf("id" to returnData.id))
    }

    @GetMapping
    fun getDepartment(
//            @RequestHeader("Authorization") encoding: String
    ): Map<String, MutableIterable<DepartmentEntity>> {
//        val user = authenticator.checkIsAuthenticate(encoding) ?: throw HttpClientErrorException(HttpStatus.FORBIDDEN)
        return mapOf("data" to departmentRepository.findAll())
    }
}
