package lab9.backend.config.kafka

import lab9.backend.domain.Event
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
@EnableKafka
class KafkaConsumerConfiguration(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {
    @Bean
    fun consumerFactory(): ConsumerFactory<String, Event> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = EventDeserializer::class.java
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, Event>
    ): ConcurrentKafkaListenerContainerFactory<String, Event> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Event>()
        factory.consumerFactory = consumerFactory
        return factory
    }

}