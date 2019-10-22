package com.f4erp.help_desk.tasks

import com.f4erp.help_desk.repositories.UserRepository
import khttp.get
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("notifyUserForAppointment")
class NotifyUserForAppointment(
        @field:Autowired private val userRepository: UserRepository
) : JavaDelegate {
    override fun execute(p0: DelegateExecution?) {
        val baseUrl = "http://bulksms.mysmsmantra.com:8080/WebSMS/SMSAPI.jsp?username=eformstim&password=473921706&sendername=EFORMS&"

        val client = userRepository.findById(p0!!.variables["client"] as String).get()
        val doctor = userRepository.findById(p0.variables["Assignee"] as String).get()

        when (p0.variables["notificationType"]) {
            "notifyForConfirm" -> {
                get(baseUrl + "mobileno=" + client.mobileNumber + "&message=" + "Your appointment with doctor " + doctor.name + " is scheduled on " + p0.variables["Date"])
                get(baseUrl + "mobileno=" + doctor.mobileNumber + "&message=" + "Your appointment with patient " + client.name + " is scheduled on " + p0.variables["Date"])
            }
            "goForAppointment" -> {
                get(baseUrl + "mobileno=" + client.mobileNumber + "&message=" + "Your appointment with doctor " + doctor.name + " is well begin shortly")
                get(baseUrl + "mobileno=" + doctor.mobileNumber + "&message=" + "Your appointment with patient " + client.name + " will being shortly")
            }
            "reschedule" -> {
                get(baseUrl + "mobileno=" + client.mobileNumber + "&message=" + "Your appointment with doctor " + doctor.name + " is canceled due to your absence")
            }
        }
    }
}
