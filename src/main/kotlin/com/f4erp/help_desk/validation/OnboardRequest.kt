package com.f4erp.help_desk.validation

import java.util.*

data class OnboardRequest(
        val name: String,
        val dateOfBirth: Date,
        val address: String,
        val doctor: String,
        val description: String
)