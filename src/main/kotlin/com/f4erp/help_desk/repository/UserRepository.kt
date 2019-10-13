package com.f4erp.help_desk.repository

import org.camunda.bpm.engine.impl.persistence.entity.UserEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
interface UserRepository : PagingAndSortingRepository<UserEntity, Long>
