package com.f4erp.help_desk.validation


data class NewAppointment(
        val Name: String,
        val Date: String,
        val Issue: String,
        val Description: String,
        val Time: String
)
