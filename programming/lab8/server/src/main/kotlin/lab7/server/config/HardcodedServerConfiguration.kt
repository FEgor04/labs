package lab7.server.config

import com.zaxxer.hikari.HikariConfig
import lab7.config.SharedConfig
import java.net.InetSocketAddress
import java.net.SocketAddress

@Suppress("MagicNumber")
class HardcodedServerConfiguration : ServerConfiguration {
    override val loadBalancerAddress: SocketAddress
        get() = InetSocketAddress("localhost", SharedConfig.LOAD_BALANCER_PORT)
    override val databaseConfiguration: HikariConfig
        get() = HikariConfig("hikari.properties")
    override val udpConfiguration: ServerConfiguration.UDPConfiguration
        get() = object : ServerConfiguration.UDPConfiguration {
            override val workersNumber: Int
                get() = 2
            override val producersNumber: Int
                get() = 2
            override val consumersNumber: Int
                get() = 10
            override val chunkSize: Int
                get() = SharedConfig.CHUNK_SIZE
        }
    override val prometheusPort: Int
        get() = 1337
}
