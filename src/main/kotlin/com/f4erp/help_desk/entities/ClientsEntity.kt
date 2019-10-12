package com.f4erp.help_desk.entities

import java.sql.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class ClientsEntity(
        @Id @GeneratedValue val id: Long,
        @Column(nullable = false) val name: String,
        @Column(nullable = false) val dateOfBirth: Date,
        @Column(nullable = true) val address: String?,
        @Column(nullable = false) val mobileNumber: String,
        @Column(nullable = false) val email: String,
        @Column(nullable = true) val firebaseID: String
)
