package lab8.server.config

import com.zaxxer.hikari.HikariConfig
import lab8.config.UDPConfiguration
import lab8.properties.getByte
import lab8.properties.getInt
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.charset.Charset
import java.util.Properties

@Suppress("MagicNumber")
class PropertiesFileConfiguration(properties: Properties) : ServerConfiguration {
    override val loadBalancerAddress: SocketAddress = InetSocketAddress(
        properties.getProperty("loadBalancer.host", "localhost"),
        properties.getInt("loadBalancer.port", 5555),
    )
    override val databaseConfiguration: HikariConfig = HikariConfig("config/hikari.properties")
    override val udpConfiguration: UDPConfiguration =
        object : UDPConfiguration {
            override val chunkSize: Int = properties.getInt("udp.chunkSize", 32 * 1024)
            override val isFinalChunkByte: Byte = properties.getByte("udp.isFinalChunkByte", 42)
            override val ackBytes: ByteArray
                get() = "ACK".toByteArray(Charset.forName("UTF-8"))
        }

    override val workersNumber: Int = properties.getInt("server.workers.number", 10)
    override val producersNumber: Int = properties.getInt("server.producers.number", 10)
    override val consumersNumber: Int = properties.getInt("server.consumers.number", 10)
    override val syncPort: Int = properties.getInt("synchronizer.port", 2280)
    override val prometheusPort: Int = properties.getInt("server.prometheus.port", 1337)
}
