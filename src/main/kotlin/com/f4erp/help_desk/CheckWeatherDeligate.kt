package com.f4erp.help_desk

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate

import java.util.Random

class CheckWeatherDeligate : JavaDelegate {
    @Throws(Exception::class)
    override fun execute(delegateExecution: DelegateExecution) {
        val random = Random()
        delegateExecution.setVariable("name", "Omkar Yada")
        delegateExecution.setVariable("weatherOK", random.nextBoolean())
    }
}
