package lab9.backend.config.kafka

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfiguration(
        @Value("\${spring.kafka.bootstrap-servers}")
        private val bootstrapServers: String,
        @Value("\${kafka.topics.events}")
        private val eventsTopic: String
) {
    @Bean
    fun topic(): NewTopic {
        return TopicBuilder.name(eventsTopic)
                .build()
    }

    @Bean
    fun admin(): KafkaAdmin {
        return KafkaAdmin(mapOf(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ))
    }
}
