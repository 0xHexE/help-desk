package com.f4erp.help_desk.entities

import javax.persistence.*

@Entity
data class DrAppointment(
        @GeneratedValue
        @Id
        private val id: Id,
        @OneToOne
        @JoinColumn
        private val clientsEntity: ClientsEntity,
        @OneToOne
        @JoinColumn
        private val doctor: DoctorEntity
)
