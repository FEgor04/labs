package lab9.backend.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
        @NotBlank
        val username: String,
        @NotBlank
        val password: String,
)