package com.f4erp.help_desk.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class UserDoctorAssignment {
    @Id
    @GeneratedValue
    var id: Long = 0

    @OneToOne
    var doctor: UserEntity? = null

    @OneToOne
    var client: UserEntity? = null
}
