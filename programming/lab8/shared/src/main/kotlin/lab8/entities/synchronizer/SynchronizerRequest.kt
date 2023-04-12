package lab8.entities.synchronizer

import kotlinx.serialization.Serializable
import lab8.entities.events.CollectionEvent

@Serializable
sealed class SynchronizerRequest(val name: String) {
    @Serializable
    data class Notify(val event: CollectionEvent): SynchronizerRequest("notify")

    @Serializable
    class Subscribe(): SynchronizerRequest("sub")

    @Serializable
    class Unsubscribe(): SynchronizerRequest("unsub")
}