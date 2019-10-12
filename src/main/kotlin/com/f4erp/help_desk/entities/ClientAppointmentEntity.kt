package com.f4erp.help_desk.entities

import java.util.*
import javax.persistence.*

@Entity
data class ClientAppointmentEntity(
        @Id @GeneratedValue val id: Long,
        @ManyToOne val client: ClientsEntity,
        @Column val notNullable: Date
)
