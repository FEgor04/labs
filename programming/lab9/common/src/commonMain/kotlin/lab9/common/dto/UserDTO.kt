package lab9.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: Int,
    val username: String,
)