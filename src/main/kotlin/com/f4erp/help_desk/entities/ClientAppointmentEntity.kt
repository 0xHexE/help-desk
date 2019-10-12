package com.f4erp.help_desk.entities

import java.util.*
import javax.persistence.*

@Entity
class ClientAppointmentEntity {
    @Id @GeneratedValue val id: Long? = null
    @ManyToOne val client: ClientEntity? = null
    @Column val notNullable: Date? = null
}