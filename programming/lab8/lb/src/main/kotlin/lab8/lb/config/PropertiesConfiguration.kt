package lab8.lb.config

import com.zaxxer.hikari.HikariConfig
import lab8.config.UDPConfiguration
import lab8.properties.getByte
import lab8.properties.getInt
import lab8.properties.getLong
import java.nio.charset.Charset
import java.util.Properties

@Suppress("MagicNumber")
class PropertiesConfiguration(private val properties: Properties) :
    LoadBalancerConfiguration {
    override val udpConfiguration: UDPConfiguration = object : UDPConfiguration {
        override val chunkSize: Int = properties.getInt("udp.chunkSize", 32 * 1024)
        override val isFinalChunkByte: Byte = properties.getByte("udp.isFinalChunkByte", 42)
        override val ackBytes: ByteArray
            get() = "ACK".toByteArray(Charset.forName("UTF-8"))
    }
    override val hikariConfig: HikariConfig
        get() = HikariConfig("config/hikari.properties")
    override val loadBalancerPort: Int
        get() = properties.getInt("loadBalancer.port", 5555)
    override val syncDelay: Long = properties.getLong("loadBalancer.syncDelay", 250)
    override val workersNumber: Int = properties.getInt("loadBalancer.workers", 10)
    override val producersNumber: Int = properties.getInt("loadBalancer.producers", 10)
    override val consumersNumber: Int = properties.getInt("loadBalancer.consumers", 10)
}
