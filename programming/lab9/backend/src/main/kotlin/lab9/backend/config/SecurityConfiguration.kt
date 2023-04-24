package lab9.backend.config

import lab9.backend.logger.KCoolLogger
import lab9.backend.users.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfiguration {
    val logger by KCoolLogger()

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService {
        return object : UserDetailsService {
            override fun loadUserByUsername(username: String?): UserDetails {
                if (username == null) {
                    throw UsernameNotFoundException("Username should not be null")
                }
                val user = userRepository.findByUsername(username)
                if (user != null) {
                    return user
                }
                throw UsernameNotFoundException("User $username not found")
            }

        }
    }


    @Bean
    fun httpSecurity(http: HttpSecurity): SecurityFilterChain {
        return http
//            .csrf { csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository()) }
            .csrf().disable()
            .cors(Customizer.withDefaults())
            .headers {
                it.frameOptions().sameOrigin().httpStrictTransportSecurity().disable()
            }
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers(HttpMethod.POST, "/api/signup").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { form ->
                form
                    .successHandler { req, res, auth ->
                        logger.info("Handling success")
                        res.status = HttpStatus.OK.value()
                    }
                    .failureHandler { req, res, auth ->
                        logger.info("Handling secure failure: $auth")
                        res.status = HttpStatus.UNAUTHORIZED.value()
                    }
                    .loginProcessingUrl("/api/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
            }
            .exceptionHandling { ex ->
                logger.error("Handling exception: $ex")
                ex.authenticationEntryPoint(HttpStatusEntryPoint((HttpStatus.UNAUTHORIZED)))
            }
            .build()
    }

    @Bean
    fun corsConfigureSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.addAllowedOrigin("http://localhost:8081")
        config.allowCredentials = true
        source.registerCorsConfiguration("/**", config)
        return source
    }
}