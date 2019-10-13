package com.f4erp.help_desk.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "client_appointment", path = "client-appointment")
interface ClientAppointment : PagingAndSortingRepository<Client, Long> {
}
