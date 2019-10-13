package com.f4erp.help_desk.entities

import java.sql.Date
import javax.persistence.*

@Entity
class AppointmentEntity {
    @GeneratedValue
    @Id
    private val id: Long? = null
    @OneToOne
    @JoinColumn
    private val clientEntity: UserEntity? = null
    @OneToOne
    @JoinColumn
    private val doctor: UserEntity? = null
    @Column(nullable = false)
    private val time: Date? = null
}
