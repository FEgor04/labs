package lab8.client.config

import lab8.config.UDPConfiguration
import lab8.properties.getByte
import lab8.properties.getInt
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.charset.Charset
import java.util.*

@Suppress("MagicNumber")
class PropertiesConfiguration(private val properties: Properties) : ClientConfiguration {
    override val udpConfig: UDPConfiguration = object : UDPConfiguration {
        override val chunkSize: Int = properties.getInt("udp.chunkSize", 32 * 1024)
        override val isFinalChunkByte: Byte = properties.getByte("udp.isFinalChunkByte", 42)
        override val ackBytes: ByteArray get() = "ACK".toByteArray(Charset.forName("UTF-8"))
    }
    override val loadBalancerAddr: SocketAddress = InetSocketAddress(
        properties.getProperty("loadBalancer.host", "localhost"),
        properties.getInt("loadBalancer.port", 5555),
    )

    override val synchronizerPort: Int = properties.getInt("synchronizer.port", 2280)
}
