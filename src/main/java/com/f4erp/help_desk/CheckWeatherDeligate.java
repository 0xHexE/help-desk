package com.f4erp.help_desk;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Random;

public class CheckWeatherDeligate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Random random = new Random();

        delegateExecution.setVariable("name", "Omkar Yada");
        delegateExecution.setVariable("weatherOK", random.nextBoolean());



    }
}
