package com.f4erp.help_desk

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("addToQueue")
class AddToQueue : JavaDelegate {
    
    private val DB = null;

    @Throws(Exception::class)
    override fun execute(execution: DelegateExecution) {
        // ToDo:
        // add vars to temporary database
    }
}
