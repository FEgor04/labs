package lab8.server.config

import com.zaxxer.hikari.HikariConfig
import lab8.config.UDPConfiguration
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.charset.Charset

@Suppress("MagicNumber")
class HardcodedServerConfiguration : ServerConfiguration {
    override val loadBalancerAddress: SocketAddress
        get() = InetSocketAddress("localhost", 5555)
    override val databaseConfiguration: HikariConfig
        get() = HikariConfig("hikari.properties")
    override val workersNumber: Int
        get() = 2
    override val producersNumber: Int
        get() = 2
    override val consumersNumber: Int
        get() = 10
    override val udpConfiguration: UDPConfiguration
        get() = object : UDPConfiguration {

            override val chunkSize: Int
                get() = 32 * 1024
            override val isFinalChunkByte: Byte
                get() = 42
            override val ackBytes: ByteArray
                get() = "ACK".toByteArray(Charset.forName("UTF-8"))
        }
    override val prometheusPort: Int
        get() = 1337
}
