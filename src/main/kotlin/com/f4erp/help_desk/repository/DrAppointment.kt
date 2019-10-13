package com.f4erp.help_desk.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "dr-appointment", path = "dr-appointment")
interface DrAppointment : PagingAndSortingRepository<Client, Long>
