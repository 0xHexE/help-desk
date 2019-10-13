package com.f4erp.help_desk.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class UserEntity {
    @GeneratedValue
    @Id
    val id: Long = 0

    @Column(nullable = false)
    val userId: String? = null

    @Column
    val userRole: String? = null

    @Column
    val name: String? = null

    @Column(nullable = true)
    val firebaseID: String? = null

    @Column(nullable = true)
    val mobileNumber: String? = null
}
