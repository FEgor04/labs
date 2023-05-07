package lab9.backend.adapter.`in`.web.vehicle

import jakarta.transaction.Transactional
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.BackendApplication
import lab9.backend.adapter.`in`.web.PostgresIntegrationTest
import lab9.backend.adapter.`in`.web.dto.CreateVehicleRequest
import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleJpaEntity
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import lab9.backend.domain.Vehicle
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class]
)
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class CreateVehicleControllerIntegrationTest(
    @Autowired
    override val userRepository: UserRepository,
    @Autowired
    override val vehicleRepository: VehicleRepository,
    @Autowired
    val mockMvc: MockMvc,
) : PostgresIntegrationTest(userRepository, vehicleRepository) {
    fun createTestUser(): UserJpaEntity {
        return userRepository.save(UserJpaEntity(null, "test", "test", emptySet(), emptySet(), emptySet()))
    }

    @Test
    @Transactional
    @WithMockUser(username = "test", password = "test")
    fun `create new`() {
        createTestUser()
        assertEquals(0, vehicleRepository.count())
        val request = CreateVehicleRequest(
            name = "teest vehicle",
            x = 1,
            y = 1,
            enginePower = 2.0,
            vehicleType = Vehicle.VehicleType.BICYCLE,
            fuelType = Vehicle.FuelType.ANTIMATTER
        )
        mockMvc.post("/api/vehicles") {
            contentType = MediaType.APPLICATION_JSON
            content = Json.encodeToString(request)
        }.andExpect {
            status { is2xxSuccessful() }
        }
        assertEquals(1, userRepository.count())
        assertEquals(1, vehicleRepository.count())
    }

    @AfterEach
    fun `clean up`() {
        vehicleRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    fun `create existing`() {
        assertEquals(0, vehicleRepository.count())
        val request = CreateVehicleRequest(
            name = "teest vehicle",
            x = 1,
            y = 1,
            enginePower = 2.0,
            vehicleType = Vehicle.VehicleType.BICYCLE,
            fuelType = Vehicle.FuelType.ANTIMATTER
        )
        val user = createTestUser()
        val vehicle = vehicleRepository.saveAndFlush(
            VehicleJpaEntity(
                null,
                request.name,
                user,
                request.x,
                request.y,
                LocalDate.now(),
                request.enginePower,
                request.vehicleType,
                request.fuelType
            )
        )
        assertEquals(1, vehicleRepository.count())
        mockMvc.post("/api/vehicles") {
            contentType = MediaType.APPLICATION_JSON
            content = Json.encodeToString(request)
        }.andExpect {
            status { isConflict() }
        }
        assertEquals(1, vehicleRepository.count())
    }

}