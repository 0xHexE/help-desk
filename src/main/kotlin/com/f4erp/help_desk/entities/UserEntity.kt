package com.f4erp.help_desk.entities

import java.sql.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class UserEntity {
    @Id()
    val uid: String? = null

    @Column(nullable = true)
    val photoUrl: String? = null

    @Column(nullable = false)
    val email: String? = null

    @Column
    val userRole: String? = null

    @Column
    val name: String? = null

    @Column(nullable = true)
    val mobileNumber: String? = null

    @Column(nullable = true)
    val address: String? = null

    @Column(nullable = true)
    val dateOfBirth: Date? = null
}
