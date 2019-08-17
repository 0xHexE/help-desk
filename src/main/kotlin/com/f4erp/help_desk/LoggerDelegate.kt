package com.f4erp.help_desk

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import java.util.logging.Logger

/**
 * This is an easy adapter implementation
 * illustrating how a Java Delegate can be used
 * from within a BPMN 2.0 Service Task.
 */
@Component("logger")
class LoggerDelegate : JavaDelegate {

    private val LOGGER = Logger.getLogger(LoggerDelegate::class.java.name)

    @Throws(Exception::class)
    override fun execute(execution: DelegateExecution) {

        LOGGER.info("\n\n  ... LoggerDelegate invoked by "
                + "processDefinitionId=" + execution.processDefinitionId
                + ", activtyId=" + execution.currentActivityId
                + ", activtyName='" + execution.currentActivityName + "'"
                + ", processInstanceId=" + execution.processInstanceId
                + ", businessKey=" + execution.processBusinessKey
                + ", executionId=" + execution.id
                + " \n\n")
    }
}
