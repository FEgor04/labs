package lab9.backend.application.port.out.events

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import lab9.backend.domain.Event

interface SubscribeToEventsPort {
    fun subscribe(): SharedFlow<Event>
}