package lab7.entities.balancer

import kotlinx.serialization.Serializable
import java.net.InetSocketAddress
import java.net.SocketAddress

@Serializable
data class NodeAddress(
    val address: String,
    val port: Int,

) {
    fun toSocketAddress(): SocketAddress {
        return InetSocketAddress(address, port)
    }

    companion object {
        fun fromInetSocketAddress(sa: InetSocketAddress): NodeAddress {
            return NodeAddress(sa.address.hostAddress, sa.port)
        }
    }
}
