package com.f4erp.help_desk.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class ClientNotificationEntity(
        @Id @GeneratedValue val id: Id,
        @Column val description: String,
        @Column val type: String
)
