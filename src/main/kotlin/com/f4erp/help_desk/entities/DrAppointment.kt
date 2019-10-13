package com.f4erp.help_desk.entities

import org.camunda.bpm.engine.impl.persistence.entity.UserEntity
import javax.persistence.*

@Entity
class DrAppointment{
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
