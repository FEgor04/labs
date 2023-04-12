package lab8.synchronizer

import config.PropertiesFileConfiguration
import config.SynchronizerConfiguration
import data.server.SyncServer
import data.synchronizer.UDPCommunicatorSynchronizer
import domain.UseCase
import kotlinx.coroutines.runBlocking
import lab8.udp.acknowledge.FlowAcknowledgeProvider
import lab8.udp.communicator.BetterDatagramSocket
import java.io.FileReader
import java.net.DatagramSocket
import java.util.*

fun main() {
    val configReader = FileReader("config/server.properties")
    val properties = Properties()
    properties.load(configReader)
    val config: SynchronizerConfiguration = PropertiesFileConfiguration(properties)

    val socket = BetterDatagramSocket(
        DatagramSocket(config.synchronizerPort),
        config.udpConfiguration.ackBytes,
        config.udpConfiguration.chunkSize,
        config.udpConfiguration.isFinalChunkByte,
        FlowAcknowledgeProvider.generate(),
    )

    val synchronizer = UDPCommunicatorSynchronizer(socket)
    val useCase = UseCase(synchronizer)
    val server = SyncServer(useCase, socket, config)
    runBlocking {
        server.run()
    }
}