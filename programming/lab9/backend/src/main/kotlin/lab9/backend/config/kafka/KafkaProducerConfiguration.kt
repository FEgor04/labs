package lab9.backend.config.kafka

import lab9.backend.domain.Event
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfiguration(
        @Value("\${spring.kafka.bootstrap-servers}")
        private val bootstrapServers: String
) {
    @Bean
    fun producerFactory(): ProducerFactory<String, Event> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    private fun producerConfig(): Map<String, Any> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = EventSerializer::class.java
        return configProps
    }


    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Event> {
        return KafkaTemplate(producerFactory())
    }
}