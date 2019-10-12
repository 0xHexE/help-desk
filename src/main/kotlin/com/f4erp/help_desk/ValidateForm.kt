package com.f4erp.help_desk

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component("validateForm")
class ValidateForm : JavaDelegate {

    @Throws(Exception::class)
    override fun execute(execution: DelegateExecution) {
        var name: String = execution.getVariable("name").toString()
        var date: String = execution.getVariable("date").toString()
        var time: String = execution.getVariable("time").toString()

//        if ( !name.matches(/^[a-z ,.'-]+$/i) ) {
//            // do something name does not
//            // match regex
//        }

        // ToDo: 
        // validate date and time
    }
}
