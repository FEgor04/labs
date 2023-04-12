package lab8.client.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import lab8.client.domain.RemoteSynchronizer
import lab8.entities.events.CollectionEvent

class EmptySynchronizer: RemoteSynchronizer {
    private val flow = MutableSharedFlow<CollectionEvent>()
    override fun subscribe(): SharedFlow<CollectionEvent> {
        return flow.asSharedFlow()
    }

    override suspend fun unsubscribe() {
        TODO("Not yet implemented")
    }


}