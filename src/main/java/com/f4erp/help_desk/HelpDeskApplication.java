package com.f4erp.help_desk;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication("HelpDesk")
public class HelpDeskApplication {
  public static void main(String... args) {
    SpringApplication.run(HelpDeskApplication.class, args);
  }
}
