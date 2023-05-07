package lab9.backend.adapter.`in`.web.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShowUserResponse(
        val id: Int,
        val username: String
)