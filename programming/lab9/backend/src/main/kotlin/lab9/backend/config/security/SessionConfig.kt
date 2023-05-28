package lab9.backend.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer

/**
 * @author fegor04
 */
@EnableRedisHttpSession
@Configuration
class SessionConfig: AbstractHttpSessionApplicationInitializer() {

}