package com.f4erp.help_desk.repositories

import com.f4erp.help_desk.entities.AppointmentEntity
import com.f4erp.help_desk.entities.UserEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AppointmentsRepository : PagingAndSortingRepository<AppointmentEntity, Long> {
    @Query(
            value = "SELECT id, date, description, process_id, issue, status, time, client_entity_uid, doctor_uid FROM appointment_entity WHERE date >= ?1 AND date <= ?2",
            countQuery = "SELECT COUNT(*) FROM appointment_entity WHERE date >= ?1 AND date <= ?2",
            nativeQuery = true
    )
    fun getAppointmentsInRange(fromDate: Date, toDate: Date): List<AppointmentEntity>

    fun getAppointmentByClientEntity(clientEntity: UserEntity): List<AppointmentEntity>
}
