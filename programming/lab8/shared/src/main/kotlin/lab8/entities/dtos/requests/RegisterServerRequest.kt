package lab8.entities.dtos.requests

import kotlinx.serialization.Serializable
import lab8.entities.balancer.NodeAddress
import lab8.entities.user.User

@Serializable
data class RegisterServerRequest(val addr: NodeAddress) : BalanceRequest() {
    override val name: String
        get() = "register_server"
    override val user: User
        get() = User.generateRandomUser()
}
