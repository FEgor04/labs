package domain

import kotlinx.coroutines.delay
import lab8.entities.events.CollectionEvent
import lab8.logger.KCoolLogger
import java.net.SocketAddress

class UseCase(private val synchronizer: Synchronizer) {
    private val logger by KCoolLogger()

    suspend fun notify(event: CollectionEvent) {
        logger.info("Notifying subscribers on event ${event}. Waiting for 500 ms")
        delay(500)
        synchronizer.notify(event)
        logger.info("Notified everyone!")
    }

    fun unsubscribe(addr: SocketAddress) {
        logger.info("Dude #${addr} unsubscribed")
        synchronizer.unsubscribe(addr)
    }

    fun addSubscriber(addr: SocketAddress) {
        logger.info("new subscriber: $addr")
        synchronizer.addSubscriber(addr)
    }
}