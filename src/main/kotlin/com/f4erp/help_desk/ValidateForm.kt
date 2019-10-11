package com.f4erp.help_desk

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("validateForm")
class ValidateForm : JavaDelegate {

    @Throws(Exception::class)
    override fun execute(execution: DelegateExecution) {
        var name: String = execute.getVariable("name")
        var date: String = execute.getVariable("date")
        var time: String = execute.getVariable("time")

        if ( !name.matches("/^[a-z ,.'-]+$/i") ) {
            // do something name does not 
            // match regex
        }

        // ToDo: 
        // validate date and time
    }
}
