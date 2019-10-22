package com.f4erp.help_desk.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class TreatmentTypeEntity {
    @GeneratedValue
    @Id
    val id: Long? = null

    @Column
    var name: String? = null
}
