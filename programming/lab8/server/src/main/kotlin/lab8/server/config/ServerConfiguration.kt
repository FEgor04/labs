package lab8.server.config

import com.zaxxer.hikari.HikariConfig
import lab8.config.UDPConfiguration
import java.net.SocketAddress

interface ServerConfiguration {
    val loadBalancerAddress: SocketAddress
    val databaseConfiguration: HikariConfig
    val udpConfiguration: UDPConfiguration
    val prometheusPort: Int
    val workersNumber: Int
    val producersNumber: Int
    val consumersNumber: Int
    val syncPort: Int
}
