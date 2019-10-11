package com.f4erp.help_desk

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("removeFromQueue")
class RemoveFromQueue : JavaDelegate {

    private val Form;

    @Throws(Exception::class)
    override fun execute(execution: DelegateExecution) {
        
    }
}
