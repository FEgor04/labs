package data.synchronizer

import domain.Synchronizer
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab8.entities.events.CollectionEvent
import lab8.logger.KCoolLogger
import lab8.udp.communicator.UDPCommunicator
import java.net.SocketAddress
import java.nio.charset.Charset

class UDPCommunicatorSynchronizer(val socket: UDPCommunicator): Synchronizer {
    val subscribers: MutableSet<SocketAddress> = HashSet()
    private val logger by KCoolLogger()

    override fun addSubscriber(addr: SocketAddress) {
        logger.info("Added new subscriber: ${addr}")
        subscribers += addr
    }

    override fun unsubscribe(addr: SocketAddress) {
        subscribers.remove(addr)
    }

    override suspend fun notify(event: CollectionEvent) {
        val data = Json.encodeToString(event).toByteArray(Charset.forName("UTF-8"))
        for(sub in subscribers) {
            socket.send(data, sub)
        }
    }
}