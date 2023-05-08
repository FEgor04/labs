package lab9.backend.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaConfiguration {
    @Bean
    fun topic(): NewTopic {
        return TopicBuilder.name("events")
            .partitions(10)
            .replicas(1)
            .build()
    }
}