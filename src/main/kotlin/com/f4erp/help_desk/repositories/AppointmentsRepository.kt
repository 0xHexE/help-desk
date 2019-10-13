package com.f4erp.help_desk.repositories

import com.f4erp.help_desk.entities.AppointmentEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AppointmentsRepository : CrudRepository<AppointmentEntity, Long>
