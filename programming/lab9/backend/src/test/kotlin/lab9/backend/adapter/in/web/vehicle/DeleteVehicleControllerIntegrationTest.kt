package lab9.backend.adapter.`in`.web.vehicle

import lab9.backend.BackendApplication
import lab9.backend.adapter.`in`.web.PostgresIntegrationTest
import lab9.backend.adapter.out.persistence.authorities.AuthoritiesRepository
import lab9.backend.adapter.out.persistence.authorities.AuthorityEntityPK
import lab9.backend.adapter.out.persistence.authorities.AuthorityJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import kotlin.test.assertEquals


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class]
)
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class DeleteVehicleControllerIntegrationTest(
    @Autowired
    val mockMvc: MockMvc,
    @Autowired
    override val vehicleRepository: VehicleRepository,
    @Autowired
    override val userRepository: UserRepository,
    @Autowired
    val authoritiesRepository: AuthoritiesRepository,
) : PostgresIntegrationTest(userRepository, vehicleRepository) {
    @Test
    @WithMockUser("test")
    fun delete() {
        val user = this.createNewUser("test", "test")
        val vehicle = this.createRandomVehicle(user)
        assertEquals(1, vehicleRepository.count())
        mockMvc.delete("/api/vehicles/${vehicle.id!!}").andExpect {
            status {
                is2xxSuccessful()
            }
        }
        assertEquals(0, vehicleRepository.count())
    }

    @BeforeEach
    @AfterEach
    fun cleanUp() {
        authoritiesRepository.deleteAll()
        vehicleRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @WithMockUser("test")
    fun `delete non existing vehicle`() {
        this.createNewUser("test", "test")
        assertEquals(0, vehicleRepository.count())
        mockMvc.delete("/api/vehicles/122352").andExpect {
            status {
                isNotFound()
            }
        }
        assertEquals(0, vehicleRepository.count())
    }

    @Test
    @WithMockUser("test")
    fun `delete other's dude vehicle`() {
        this.createNewUser("test", "test")
        val otherDude = this.createNewUser("otherDude", "test")
        val vehicle = this.createRandomVehicle(otherDude)
        assertEquals(1, vehicleRepository.count())
        mockMvc.delete("/api/vehicles/${vehicle.id}").andExpect {
            status {
                isForbidden()
            }
        }
        assertEquals(1, vehicleRepository.count())
    }

    @Test
    @WithMockUser("test")
    fun `delete other's dude vehicle with authority`() {
        val testUser = createNewUser("test", "test")
        val otherDude = createNewUser("otherDude", "test")
        authoritiesRepository.saveAndFlush(
            AuthorityJpaEntity(
                id = AuthorityEntityPK(ownerId = otherDude.id!!, authorizedId = testUser.id!!),
                owner = otherDude,
                authorizedTo = testUser,
                canEdit = true,
                canDelete = true,
            )
        )
        val vehicle = this.createRandomVehicle(otherDude)
        assertEquals(1, vehicleRepository.count())
        mockMvc.delete("/api/vehicles/${vehicle.id}").andExpect {
            status {
                is2xxSuccessful()
            }
        }
        assertEquals(0, vehicleRepository.count())
    }
}