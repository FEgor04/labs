package lab9.backend.adapter.`in`.web.vehicle

import jakarta.transaction.Transactional
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.BackendApplication
import lab9.backend.adapter.`in`.web.PostgresIntegrationTest
import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.adapter.`in`.web.dto.ShowVehiclesResponse
import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleJpaEntity
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import lab9.backend.domain.Vehicle
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate
import kotlin.math.min

const val mockedUserName = "getVehiclesUser"
const val mockedUserPassword = "test"

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class]
)
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class GetVehiclesControllerIntegrationTest(
    @Autowired
    override val userRepository: UserRepository,
    @Autowired override val vehicleRepository: VehicleRepository,
    @Autowired
    val mockMvc: MockMvc,
    @Autowired
    private val objectAdapter: WebObjectAdapter,
) : PostgresIntegrationTest(userRepository, vehicleRepository) {

    var mockedUser = UserJpaEntity(null, mockedUserName, mockedUserPassword, emptySet(), emptySet(), emptySet())

    @Test
    @WithMockUser(username = mockedUserName, password = mockedUserPassword)
    fun `empty database`() {
        val expected = ShowVehiclesResponse(
            0,
            0,
            emptyList(),
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
    @WithMockUser(username = mockedUserName, password = mockedUserPassword)
    fun `bad request (page size is negative)`() {
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

    @Test
    @WithMockUser(username = mockedUserName, password = mockedUserPassword)
    fun `bad request (page size is too big)`() {
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

    @Test
    @WithMockUser(username = mockedUserName, password = mockedUserPassword)
    fun `bad request (page number is negative)`() {
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

    @Test
    @Transactional
    @WithMockUser(username = mockedUserName, password = mockedUserPassword)
    fun `no filters or sorting`() {
        val vehicles = generateVehicles()
        assert(vehicleRepository.count() == vehicles.size.toLong())
        for (pageSize in 10..50 step 5) {
            for (pageNumber in 0..(vehicles.size).div(pageSize) step 5) {
                val expectedVehicles =
                    vehicles.subList(pageNumber * pageSize, min((pageNumber + 1) * pageSize, vehicles.size))
                        .map { objectAdapter.vehicleToResponse(it.toDomainEntity()) }
                val expectedResponse = ShowVehiclesResponse(
                    vehicles = expectedVehicles,
                    totalPages = (vehicles.size + pageSize - 1).div(pageSize), // округление вверх
                    totalElements = vehicles.size
                )
                val expectedJson = Json.encodeToString(expectedResponse)
                mockMvc.get("/api/vehicles") {
                    param("pageSize", pageSize.toString())
                    param("pageNumber", pageNumber.toString())
                }.andExpect {
                    content {
                        json(expectedJson)
                    }
                    status {
                        is2xxSuccessful()
                    }
                }
            }
        }
    }

    @BeforeEach
    fun `insert mock user`() {
        mockedUser = userRepository.save(mockedUser)
    }

    private fun generateVehicles(owner: UserJpaEntity = mockedUser, number: Int = 1000): List<VehicleJpaEntity> {
        val vehicles = mutableListOf<VehicleJpaEntity>()
        repeat(number) { i ->
            val newVehicle = vehicleRepository.save(
                VehicleJpaEntity(
                    null,
                    i.toString(),
                    owner,
                    i,
                    i.toLong(),
                    LocalDate.now(),
                    i * 1.0,
                    Vehicle.VehicleType.BICYCLE,
                    Vehicle.FuelType.ANTIMATTER,
                )
            )
            vehicles.add(newVehicle)
        }
        return vehicles
    }

    @AfterEach
    fun `clean up mock user`() {
        userRepository.delete(mockedUser)
    }

}