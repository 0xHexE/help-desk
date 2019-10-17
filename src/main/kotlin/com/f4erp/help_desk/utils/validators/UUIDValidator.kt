package com.f4erp.help_desk.utils.validators

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext
import java.util.*


class UUIDValidator : FormFieldValidator {
    override fun validate(submittedValue: Any, validatorContext: FormFieldValidatorContext): Boolean {
        if (submittedValue == "") {
            return true
        }
        return try {
            UUID.fromString(submittedValue.toString())
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
    }
}
