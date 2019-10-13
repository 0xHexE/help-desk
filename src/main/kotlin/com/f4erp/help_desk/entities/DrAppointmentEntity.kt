package com.f4erp.help_desk.entities

import javax.persistence.*

@Entity
class DrAppointmentEntity {
    @GeneratedValue
    @Id
    private val id: Long? = null
    @OneToOne
    @JoinColumn
    private val clientEntity: UserEntity? = null
    @OneToOne
    @JoinColumn
    private val doctor: UserEntity? = null
}
