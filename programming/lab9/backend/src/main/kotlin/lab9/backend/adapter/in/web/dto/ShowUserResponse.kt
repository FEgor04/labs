package lab9.backend.adapter.`in`.web.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShowUserResponse(
        val id: Int,
        val username: String,
        val canYouEdit: Boolean,
        val canYouDelete: Boolean,
        val canHeDelete: Boolean,
        val canHeEdit: Boolean,
)