package lab7.server.config

import com.zaxxer.hikari.HikariConfig
import java.net.SocketAddress

interface ServerConfiguration {
    val loadBalancerAddress: SocketAddress
    val databaseConfiguration: HikariConfig
    val udpConfiguration: UDPConfiguration
    val prometheusPort: Int

    interface UDPConfiguration {
        val workersNumber: Int
        val producersNumber: Int
        val consumersNumber: Int
        val chunkSize: Int
    }
}
