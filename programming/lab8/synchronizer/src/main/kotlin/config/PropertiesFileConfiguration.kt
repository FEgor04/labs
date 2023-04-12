package config

import lab8.config.UDPConfiguration
import lab8.properties.getByte
import lab8.properties.getInt
import java.nio.charset.Charset
import java.util.Properties

@Suppress("MagicNumber")
class PropertiesFileConfiguration(properties: Properties) : SynchronizerConfiguration {
    override val synchronizerPort = properties.getInt("synchronizer.port", 2280)
    override val udpConfiguration: UDPConfiguration =
        object : UDPConfiguration {
            override val chunkSize: Int = properties.getInt("udp.chunkSize", 32 * 1024)
            override val isFinalChunkByte: Byte = properties.getByte("udp.isFinalChunkByte", 42)
            override val ackBytes: ByteArray
                get() = "ACK".toByteArray(Charset.forName("UTF-8"))
        }
    override val workersNumber: Int = properties.getInt("server.workers.number", 2)
    override val producersNumber: Int = properties.getInt("server.producers.number", 2)
    override val consumersNumber: Int = properties.getInt("server.consumers.number", 2)
}
