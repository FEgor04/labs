package lab9.backend.adapter.out.events.stub

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import lab9.backend.application.port.out.events.SendEventPort
import lab9.backend.application.port.out.events.SubscribeToEventsPort
import lab9.backend.domain.Event
import lab9.backend.logger.KCoolLogger
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

//@Component
//@Primary
/**
 * In-memory stream для ивентов
 */
class StubEventsStream : SendEventPort, SubscribeToEventsPort {
    private val logger by KCoolLogger()
    private val flow: MutableSharedFlow<Event> =
        MutableSharedFlow(
            extraBufferCapacity = 1000,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        ) // tryEmit awlays returns true

    override fun send(event: Event) {
        logger.info("New event: $event")
        flow.tryEmit(event)
    }

    override fun subscribe(): SharedFlow<Event> {
        logger.info("New subscriber")
        return this.flow.asSharedFlow()
    }
}