package lab8.server.domain.notifier

import lab8.entities.events.CollectionEvent

interface Notifier {
    suspend fun notify(event: CollectionEvent)
}