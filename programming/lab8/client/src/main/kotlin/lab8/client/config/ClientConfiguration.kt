package lab8.client.config

import lab8.config.UDPConfiguration
import java.net.SocketAddress

interface ClientConfiguration {
    val udpConfig: UDPConfiguration
    val loadBalancerAddr: SocketAddress
}
