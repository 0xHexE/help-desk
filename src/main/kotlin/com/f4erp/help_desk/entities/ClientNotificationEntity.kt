package com.f4erp.help_desk.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class ClientNotificationEntity {
    @Id @GeneratedValue val id: Long? = null
    @Column val description: String? = null
    @Column val type: String? = null
}
