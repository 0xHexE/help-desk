package com.f4erp.help_desk.repositories

import com.f4erp.help_desk.entities.UserEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : PagingAndSortingRepository<UserEntity, String> {
}
