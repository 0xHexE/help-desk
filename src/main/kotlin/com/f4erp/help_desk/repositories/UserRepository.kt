package com.f4erp.help_desk.repositories

import com.f4erp.help_desk.entities.UserEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : PagingAndSortingRepository<UserEntity, String> {
    @Query(value = "SELECT * FROM user_entity WHERE user_role = ?1", nativeQuery = true)
    fun findByUserRole(userRole: String): List<UserEntity>
}
