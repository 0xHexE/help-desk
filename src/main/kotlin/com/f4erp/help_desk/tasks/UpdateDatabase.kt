package com.f4erp.help_desk.tasks

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("updateDatabase")
class UpdateDatabase : JavaDelegate {
    override fun execute(p0: DelegateExecution?) {
    }
}
