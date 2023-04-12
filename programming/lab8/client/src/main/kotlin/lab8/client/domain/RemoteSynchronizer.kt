package lab8.client.domain

import kotlinx.coroutines.flow.SharedFlow
import lab8.entities.events.CollectionEvent

interface RemoteSynchronizer {
    fun subscribe(): SharedFlow<CollectionEvent>
    suspend fun unsubscribe()
}