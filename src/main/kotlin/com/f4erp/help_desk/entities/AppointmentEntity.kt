package com.f4erp.help_desk.entities

import java.util.*
import javax.persistence.*

@Entity
class AppointmentEntity {
    @GeneratedValue
    @Id
    val id: Long? = null
    @OneToOne
    @JoinColumn
    var clientEntity: UserEntity? = null
    @OneToOne
    @JoinColumn
    var doctor: UserEntity? = null
    @Column(nullable = false)
    var date: Date? = null
    @Column
    var issue: String? = null
    @Column
    var description: String? = null
    @Column
    var time: Long? = null
    @Column
    val status: String? = null
}
