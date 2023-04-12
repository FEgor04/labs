package lab8.server.data.notifier

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab8.config.UDPConfiguration
import lab8.entities.events.CollectionEvent
import lab8.entities.synchronizer.SynchronizerRequest
import lab8.logger.KCoolLogger
import lab8.server.domain.notifier.Notifier
import lab8.udp.communicator.BetterDatagramSocket
import lab8.udp.communicator.UDPCommunicator
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.nio.charset.Charset

class UDPNotifier(syncPort: Int, private val udpConfiguration: UDPConfiguration): Notifier {
    private val synchronizerAddr = InetSocketAddress("localhost", syncPort)
    private val logger by KCoolLogger()
    private val socket: UDPCommunicator = BetterDatagramSocket(
        DatagramSocket(),
        udpConfiguration.ackBytes,
        udpConfiguration.chunkSize,
        udpConfiguration.isFinalChunkByte,
    )

    override suspend fun notify(event: CollectionEvent) {
        logger.info("Sending data to synchronizer")
        val dataBytes = try {
            Json.encodeToString<SynchronizerRequest>(SynchronizerRequest.Notify(event)).toByteArray(Charset.forName("UTF-8"))
        }
        catch(e: Exception) {
            logger.error("Could not send notification to notifier: $e")
            return
        }
        socket.send(dataBytes, synchronizerAddr)
        socket.receive()
    }

}