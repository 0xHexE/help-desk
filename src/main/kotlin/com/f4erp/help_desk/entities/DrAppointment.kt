package com.f4erp.help_desk.entities

import javax.persistence.*

@Entity
class DrAppointment{
    @GeneratedValue
    @Id
    private val id: Long? = null
    @OneToOne
    @JoinColumn
    private val clientEntity: ClientEntity? = null
    @OneToOne
    @JoinColumn
    private val doctor: DoctorEntity? = null
}