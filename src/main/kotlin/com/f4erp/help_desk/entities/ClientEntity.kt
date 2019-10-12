package com.f4erp.help_desk.entities

import java.sql.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class ClientEntity {
    @Id @GeneratedValue val id: Long? = null
    @Column(nullable = false) val name: String? = null
    @Column(nullable = false) val dateOfBirth: Date? = null
    @Column(nullable = true) val address: String? = null
    @Column(nullable = false) val mobileNumber: String? = null
    @Column(nullable = false) val email: String? = null
    @Column(nullable = true) val firebaseID: String? = null
}
