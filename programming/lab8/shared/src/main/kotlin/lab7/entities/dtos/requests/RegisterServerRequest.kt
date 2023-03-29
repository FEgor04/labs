package lab7.entities.dtos.requests

import kotlinx.serialization.Serializable
import lab7.entities.user.User

@Serializable
class RegisterServerRequest : RequestDTO(RequestType.BALANCE) {
    override val name: String
        get() = "register_server"
    override val user: User
        get() = User.generateRandomUser()
}
