package com.f4erp.help_desk.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "client_notification", path = "client-notification")
interface ClientNotification : PagingAndSortingRepository<Client, Long> {
}
