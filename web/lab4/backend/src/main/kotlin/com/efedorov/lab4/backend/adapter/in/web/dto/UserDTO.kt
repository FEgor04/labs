package com.efedorov.lab4.backend.adapter.`in`.web.dto

import com.efedorov.lab4.backend.common.DTO
import com.efedorov.lab4.backend.domain.User

@DTO
data class UserDTO(val id: Long, val userName: String) {
    companion object {
        fun fromEntity(entity: User.withID) = UserDTO(entity.id.value, entity.email)
    }
}