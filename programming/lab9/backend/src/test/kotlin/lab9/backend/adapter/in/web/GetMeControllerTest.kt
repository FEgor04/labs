package lab9.backend.adapter.`in`.web

import jakarta.transaction.Transactional
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.BackendApplication
import lab9.backend.adapter.out.persistence.UserJpaEntity
import lab9.backend.adapter.out.persistence.UserRepository
import lab9.common.responses.ShowUserResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.query.JpaEntityGraph
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
class GetMeControllerTest(
    @Autowired
    override val mockMvc: MockMvc,
    @Autowired
    val userRepository: UserRepository,
) : WebIntegrationTest(mockMvc) {
    @Test
    @Transactional
    @WithMockUser(username = "test", password = "test")
    fun `get existing user`() {
        val actualUser = userRepository.saveAndFlush(UserJpaEntity(null, "test", "test", emptySet()))
        val expectedContent = ShowUserResponse(actualUser.id!!, actualUser.username)
        mockMvc.get("/api/me").andExpect {
            content {
                json(
                    Json.encodeToString(expectedContent)
                )
            }
        }
    }

    @Test
    @Transactional
    fun `get with no session`() {
        mockMvc.get("/api/me").andExpect {
            status {
                isUnauthorized()
            }
        }
    }

}