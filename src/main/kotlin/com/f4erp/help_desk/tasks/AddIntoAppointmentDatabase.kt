package com.f4erp.help_desk.tasks

import com.f4erp.help_desk.entities.AppointmentEntity
import com.f4erp.help_desk.repositories.AppointmentsRepository
import com.f4erp.help_desk.repositories.DepartmentRepository
import com.f4erp.help_desk.repositories.TreatmentRepository
import com.f4erp.help_desk.repositories.UserRepository
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component("addIntoAppointmentDatabase")
class AddIntoAppointmentDatabase(
        @field:Autowired private val appointmentsRepository: AppointmentsRepository,
        @field:Autowired private val userRepository: UserRepository,
        @field:Autowired private val departmentRepository: DepartmentRepository,
        @field:Autowired private val treatmentRepository: TreatmentRepository
) : JavaDelegate {
    override fun execute(p0: DelegateExecution?) {
        val appointment = AppointmentEntity()
        appointment.issue = p0!!.getVariable("Issue") as String
        appointment.description = p0.getVariable("Description") as String
        appointment.date = p0.getVariable("Date") as Date
        appointment.time = p0.getVariable("Time") as Long
        appointment.doctor = userRepository.findById(p0.getVariable("Assignee") as String).get()
        appointment.clientEntity = userRepository.findById(p0.getVariable("client") as String).get()
        appointment.processId = p0.processInstanceId
        if (p0.hasVariable("Department")) {
            appointment.departmentEntity = departmentRepository.findById(p0.getVariable("Department") as Long).get()
        }
        if (p0.hasVariable("TreatmentType")) {
            appointment.treatmentTypeEntity = treatmentRepository.findById(p0.getVariable("TreatmentType") as Long).get()
        }
        appointmentsRepository.save(appointment)
    }
}
