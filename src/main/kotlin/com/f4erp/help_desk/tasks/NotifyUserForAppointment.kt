package com.f4erp.help_desk.tasks

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("notifyUserForAppointment")
class NotifyUserForAppointment : JavaDelegate {
    override fun execute(p0: DelegateExecution?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
