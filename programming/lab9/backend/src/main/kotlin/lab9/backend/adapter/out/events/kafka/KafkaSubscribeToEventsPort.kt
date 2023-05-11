package lab9.backend.adapter.out.events.kafka

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import lab9.backend.application.port.out.events.SubscribeToEventsPort
import lab9.backend.domain.Event
import lab9.backend.logger.KCoolLogger
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class KafkaSubscribeToEventsPort : SubscribeToEventsPort {
    private val events: MutableSharedFlow<Event> =
            MutableSharedFlow(10000, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val logger by KCoolLogger()
    private val uuid = UUID.randomUUID()
    override fun subscribe(): SharedFlow<Event> {
        return events.asSharedFlow()
    }

    @KafkaListener(id = "kafka.events.listener", topics = ["events"], groupId = "\${random.uuid}")
    fun consume(event: Event) {
        logger.info("Consumed $event")
        events.tryEmit(event)
    }
}