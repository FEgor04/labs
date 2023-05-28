package lab9.backend.adapter.out.events.kafka

import lab9.backend.application.port.out.events.SendEventPort
import lab9.backend.domain.Event
import lab9.backend.logger.KCoolLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class KafkaSendEventPort(
    @Value("\${kafka.topics.events}")
    private val topic: String,
    private val kafkaTemplate: KafkaTemplate<String, Event>
) : SendEventPort {
    private val logger by KCoolLogger()
    override fun send(event: Event) {
        logger.info("Sending message to kafka {}", event)
        val message = MessageBuilder
            .withPayload(event)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .build()
        val future = kafkaTemplate.send(message)
        future.whenComplete { result, ex ->
            if(ex != null) {
                logger.info("Error on sending event to Kafka ${ex}")
            }
            else {
                logger.info("Event sent succesfully. Result: $result")
            }
        }
    }
}