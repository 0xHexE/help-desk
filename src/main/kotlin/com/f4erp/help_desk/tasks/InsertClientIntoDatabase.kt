package com.f4erp.help_desk.tasks

import com.f4erp.help_desk.entities.UserDoctorAssignment
import com.f4erp.help_desk.entities.UserEntity
import com.f4erp.help_desk.repositories.UserDoctorAssignmentRepository
import com.f4erp.help_desk.repositories.UserRepository
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component("insertClientIntoDatabase")
class InsertClientIntoDatabase(
        @field:Autowired private val userRepository: UserRepository,
        @field:Autowired private val userDoctorAssignmentRepository: UserDoctorAssignmentRepository
) : JavaDelegate {
    override fun execute(p0: DelegateExecution?) {
        val user = UserEntity()
        user.address = p0!!.getVariable("Address") as String
        user.name = p0.getVariable("Name") as String
        user.dateOfBirth = p0.getVariable("DateOfBirth") as Date
        user.uid = p0.getVariable("uid") as String
        user.userRole = "client"
        userRepository.save(user)
        val userDoctorAssignment = UserDoctorAssignment()
        userDoctorAssignment.client = user
        val doctor = UserEntity()
        doctor.uid = p0.getVariable("Doctor") as String
        userDoctorAssignment.doctor = doctor
        userDoctorAssignmentRepository.save(userDoctorAssignment)
    }
}
