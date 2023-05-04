package lab9.backend.adapter.`in`.web

import jakarta.transaction.Transactional
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.BackendApplication
import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleJpaEntity
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import lab9.common.dto.CoordinatesDTO
import lab9.common.requests.CreateVehicleRequest
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType
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
    override val mockMvc: MockMvc,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val vehicleRepository: VehicleRepository,
) : WebIntegrationTest(mockMvc) {

    @Test
    @Transactional
    @WithMockUser(username = "test", password = "test")
    fun `create new`() {
        assert(vehicleRepository.count() == 0.toLong())
        val request = CreateVehicleRequest(
            name = "teest vehicle",
            coordinates = CoordinatesDTO(1, 1),
            enginePower = 2.0,
            vehicleType = VehicleType.BICYCLE,
            fuelType = FuelType.ANTIMATTER
        )
        userRepository.save(UserJpaEntity(1, "test", "password", emptySet()))
        mockMvc.post("/api/vehicles") {
            this.content = Json.encodeToString(request)
            this.contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status {
                is2xxSuccessful()
            }
        }
        assert(userRepository.count() == 1.toLong())
        assert(vehicleRepository.count() == 1.toLong())
    }

    @Test
    @Transactional
    @WithMockUser(username = "test", password = "test")
    fun `create existing`() {
        assert(vehicleRepository.count() == 0.toLong())
        val request = CreateVehicleRequest(
            name = "teest vehicle",
            coordinates = CoordinatesDTO(1, 1),
            enginePower = 2.0,
            vehicleType = VehicleType.BICYCLE,
            fuelType = FuelType.ANTIMATTER
        )
        val user = userRepository.save(UserJpaEntity(1, "test", "password", emptySet()))
        val vehicle = vehicleRepository.save(
            VehicleJpaEntity(
                null,
                request.name,
                user,
                request.coordinates.x,
                request.coordinates.y,
                LocalDate.now(),
                request.enginePower,
                request.vehicleType,
                request.fuelType
            )
        )
        assert(vehicleRepository.count() == 1.toLong())
        mockMvc.post("/api/vehicles") {
            this.content = Json.encodeToString(request)
            this.contentType = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status {
                is4xxClientError()
            }
        }
    }
}