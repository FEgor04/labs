package lab9.common.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val username: String,
    val password: String,

) {
    init {
        require(username.isNotBlank()) { "Username must not be blank" }
        require(password.isNotBlank()) { "Password must not be blank" }
    }
}
