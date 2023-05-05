package lab9.backend.application.port.out.notification

import kotlinx.coroutines.flow.SharedFlow
import lab9.backend.domain.Event

interface SubscribeToEventsPort {
    fun subscribe(): SharedFlow<Event>
}