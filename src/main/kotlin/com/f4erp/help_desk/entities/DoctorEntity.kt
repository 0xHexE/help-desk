package com.f4erp.help_desk.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class DoctorEntity {
    @GeneratedValue @Id private val id: Long? = null
    @Column val name: String? = null
    @Column val email: String? = null
    @Column val firebaseID: String? = null
    @Column val mobileNumber: String? = null
}
