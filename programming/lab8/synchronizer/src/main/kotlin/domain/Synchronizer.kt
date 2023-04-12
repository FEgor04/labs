package domain

import lab8.entities.events.CollectionEvent
import java.net.SocketAddress

interface Synchronizer {
    fun addSubscriber(addr: SocketAddress)
    fun unsubscribe(addr: SocketAddress)
    suspend fun notify(event: CollectionEvent)
}