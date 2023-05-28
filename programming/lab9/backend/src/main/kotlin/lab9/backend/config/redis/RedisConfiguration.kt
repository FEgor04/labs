package lab9.backend.config.redis

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.json.Json
import lab9.backend.logger.KCoolLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.nio.charset.Charset

@Configuration
class RedisConfiguration(
        @Value("\${spring.data.redis.host}")
        private val hostname: String,
        @Value("\${spring.data.redis.port}")
        private val port: Int
) {
    @Bean
    fun redisCacheConfiguration(mapper: ObjectMapper): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer(mapper)));
    }


}