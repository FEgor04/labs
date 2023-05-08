package lab9.backend.adapter.out.events.kafka

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.application.port.out.notification.SendEventPort
import lab9.backend.domain.Event
import lab9.backend.logger.KCoolLogger
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaSendEventPort(
    private val kafkaTemplate: KafkaTemplate<String, String>
) : SendEventPort {
    private val logger by KCoolLogger()
    private val serializer = Json
    override fun send(event: Event) {
        logger.info("Sending event $event to Kafka")
        val future = kafkaTemplate.send("events", serializer.encodeToString(event))
    }
}