package com.f4erp.help_desk.tasks

import com.f4erp.help_desk.entities.AppointmentEntity
import com.f4erp.help_desk.repositories.AppointmentsRepository
import com.f4erp.help_desk.repositories.UserRepository
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import java.util.*

@Component("addIntoAppointmentDatabase")
class AddIntoAppointmentDatabase(
        private val appointmentsRepository: AppointmentsRepository,
        private val userRepository: UserRepository
) : JavaDelegate {
    override fun execute(p0: DelegateExecution?) {
        val appointment = AppointmentEntity()
        appointment.issue = p0!!.getVariable("Issue") as String
        appointment.description = p0.getVariable("Description") as String
        appointment.date = p0.getVariable("Date") as Date
        appointment.time = p0.getVariable("Time") as Long
        appointment.doctor = userRepository.findById(p0.getVariable("Assignee") as String).get()
        appointment.clientEntity = userRepository.findById(p0.getVariable("client") as String).get()
        appointmentsRepository.save(appointment)
    }
}
