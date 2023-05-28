package lab9.backend.adapter.`in`.web

import jakarta.transaction.Transactional
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.BackendApplication
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
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class]
)
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class GetMeControllerIntegrationTest(
    @Autowired
    val mockMvc: MockMvc,
    @Autowired
    override val userRepository: UserRepository,
    @Autowired
    override val vehicleRepository: VehicleRepository,
) : PostgresIntegrationTest(userRepository, vehicleRepository) {
    @Test
    @Transactional
    @WithMockUser(username = "test", password = "test")
    fun `get existing user`() {
        val actualUser = userRepository.saveAndFlush(UserJpaEntity(null, "test", "test", emptySet(), emptySet(), emptySet()))
        val expectedContent = ShowUserResponse(actualUser.id!!, actualUser.username, true, true, true, true)
        mockMvc.get("/api/me").andExpect {
            content {
                json(
                    Json.encodeToString(expectedContent)
                )
            }
        }
        print("test")
    }

}