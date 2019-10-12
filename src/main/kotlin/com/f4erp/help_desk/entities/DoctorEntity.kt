package com.f4erp.help_desk.entities

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id

data class DoctorEntity(
        @GeneratedValue @Id private val id: Id,
        @Column val name: String,
        @Column val email: String,
        @Column val firebaseID: String,
        @Column val mobileNumber: String
)
