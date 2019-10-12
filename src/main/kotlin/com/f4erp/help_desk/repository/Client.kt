package com.f4erp.help_desk.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "client", path = "client")
interface Client : PagingAndSortingRepository<Client, Long> {
}
