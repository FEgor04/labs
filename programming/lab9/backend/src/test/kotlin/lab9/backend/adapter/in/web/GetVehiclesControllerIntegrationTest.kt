package lab9.backend.adapter.`in`.web

import jakarta.transaction.Transactional
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.BackendApplication
import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleJpaEntity
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import lab9.backend.domain.Vehicle
import lab9.common.responses.ShowVehiclesResponse
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.lang.Exception
import java.time.LocalDate

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class]
)
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class GetVehiclesControllerIntegrationTest(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val vehiclesRepository: VehicleRepository,
    @Autowired
    override val mockMvc: MockMvc,
) : WebIntegrationTest(mockMvc) {

    @Test
    @WithMockUser("test")
    fun `empty database`() {
        val expected = ShowVehiclesResponse(
            emptyArray(),
            0,
            0,
        )
        val expectedJson = Json.encodeToString(expected)
        mockMvc.get("/api/vehicles").andExpect {
            status {
                is2xxSuccessful()
            }
            content {
                json(expectedJson)
            }
        }
    }

    @Test
    @WithMockUser("test")
    fun `bad request (page size is negative)`() {
        assertThrows<Exception> {
            mockMvc.get("/api/vehicles") {
                param("pageSize", "-10")
                param("pageNumber", "0")
            }
                .andExpect {
                    status {
                        isBadRequest()
                    }

                }
        }
    }

    @Test
    @WithMockUser("test")
    fun `bad request (page size is too big)`() {
        assertThrows<Exception> {
            mockMvc.get("/api/vehicles") {
                param("pageSize", "1000")
                param("pageNumber", "0")
            }
                .andExpect {
                    status {
                        isBadRequest()
                    }
                }
        }
    }

    @Test
    @WithMockUser("test")
    fun `bad request (page number is negative)`() {
        assertThrows<Exception> {
            mockMvc.get("/api/vehicles") {
                param("pageSize", "10")
                param("pageNumber", "-100")
            }
                .andExpect {
                    status {
                        isBadRequest()
                    }
                }
        }
    }

    @Test
    @Transactional
    @WithMockUser("test")
    fun `should be ok`() {
        val user = userRepository.save(UserJpaEntity(null, "test", "test", emptySet()))
        val vehicles = mutableListOf<Vehicle>()
        val vehiclesNumber = 1000
        repeat(vehiclesNumber) { i ->
            val newVehicle = vehiclesRepository.save(
                VehicleJpaEntity(
                    i,
                    i.toString(),
                    user,
                    i,
                    i.toLong(),
                    LocalDate.now(),
                    i * 1.0,
                    VehicleType.BICYCLE,
                    FuelType.ANTIMATTER,
                )
            )
            vehicles.add(newVehicle.toDomainEntity())
        }
        
    }


}