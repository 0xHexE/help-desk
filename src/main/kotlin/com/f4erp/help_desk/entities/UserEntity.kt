package com.f4erp.help_desk.entities

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class UserEntity {
    @Id()
    var uid: String? = null

    @Column(nullable = true)
    val photoUrl: String? = null

    @Column(nullable = true)
    var email: String? = null

    @Column
    var userRole: String? = null

    @Column
    var name: String? = null

    @Column(nullable = true)
    var mobileNumber: String? = null

    @Column(nullable = false)
    var address: String? = null

    @Column(nullable = true)
    var dateOfBirth: Date? = null
}
