package lab9.backend.adapter.`in`.web

import jakarta.transaction.Transactional
import kotlinx.serialization.json.Json
import lab9.backend.BackendApplication
import org.junit.ClassRule
import kotlinx.serialization.encodeToString
import lab9.backend.adapter.out.persistence.UserJpaEntity
import lab9.backend.adapter.out.persistence.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class]
)
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class SignUpControllerIntegrationTest(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    override val mockMvc: MockMvc,
): WebIntegrationTest(mockMvc) {

    @Test
    @Transactional
    fun `sign up new user`() {
        val username = "test"
        mockMvc.post("/api/signup") {
            content = Json.encodeToString(
                mapOf(
                    "username" to username,
                    "password" to username
                )
            )
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                is2xxSuccessful()
            }
        }
        assert(userRepository.count() == 1.toLong()) {
            "users number should be 1"
        }
    }

    @Test
    @Transactional
    fun `sign up existing user`() {
        val username = "user"
        userRepository.save(UserJpaEntity(null, username, "1234", emptySet()))
        userRepository.flush()
        assert(userRepository.count() == 1.toLong())
        mockMvc.post("/api/signup") {
            content = Json.encodeToString(
                mapOf(
                    "username" to username,
                    "password" to username
                )
            )
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                is4xxClientError()
            }
        }
    }

    fun createUsers() {

    }
}