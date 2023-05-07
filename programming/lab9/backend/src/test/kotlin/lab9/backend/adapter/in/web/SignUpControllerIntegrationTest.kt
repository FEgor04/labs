package lab9.backend.adapter.`in`.web

import jakarta.transaction.Transactional
import kotlinx.serialization.json.Json
import lab9.backend.BackendApplication
import kotlinx.serialization.encodeToString
import lab9.backend.adapter.`in`.web.dto.ShowUserResponse
import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class]
)
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class SignUpControllerIntegrationTest(
    @Autowired
    override val userRepository: UserRepository,
    @Autowired
    override val vehicleRepository: VehicleRepository,
    @Autowired
    val mockMvc: MockMvc,
): PostgresIntegrationTest(userRepository, vehicleRepository) {

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
            content {
                jsonPath("username") {
                    this.isString()
                    this.value(username)
                }
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
        userRepository.save(UserJpaEntity(null, username, "1234", emptySet(), emptySet(), emptySet()))
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