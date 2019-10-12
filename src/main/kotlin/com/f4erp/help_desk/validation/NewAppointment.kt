package com.f4erp.help_desk.validation

import spinjar.com.sun.istack.NotNull

data class NewAppointment(
        @NotNull
        val Date: String,
        @NotNull
        val Issue: String,
        val Description: String,
        val Time: String
)
