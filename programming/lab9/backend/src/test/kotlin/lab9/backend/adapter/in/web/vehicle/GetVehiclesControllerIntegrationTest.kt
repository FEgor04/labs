package lab9.backend.adapter.`in`.web.vehicle

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import lab9.backend.BackendApplication
import lab9.backend.adapter.`in`.web.PostgresIntegrationTest
import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.adapter.`in`.web.dto.ShowVehiclesResponse
import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleJpaEntity
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.validation.annotation.Validated
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.min
import kotlin.random.Random

const val mockedUserName = "getVehiclesUser"
const val mockedUserPassword = "test"

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [BackendApplication::class])
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
class GetVehiclesControllerIntegrationTest(
        @Autowired override val userRepository: UserRepository,
        @Autowired override val vehicleRepository: VehicleRepository,
        @Autowired val mockMvc: MockMvc,
        @Autowired private val objectAdapter: WebObjectAdapter,
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
        }.andExpect {
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
        }.andExpect {
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
        }.andExpect {
            status {
                isBadRequest()
            }
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = mockedUserName, password = mockedUserPassword)
    fun `no filters or sorting`() {
        val vehicles = generateVehicles().sortedBy { it.id }
        assert(vehicleRepository.count() == vehicles.size.toLong())
        getXData().forEach {
            val pageSize = it.first
            val pageNumber = it.second
            println("======TESTING FOR PAGE SIZE = $pageSize PAGE NUMBER = $pageNumber======")
            val expectedVehicles = vehicles.subList(pageNumber * pageSize, min((pageNumber + 1) * pageSize, vehicles.size)).map { vehicle ->
                objectAdapter
                        .vehicleToResponse(vehicle.toDomainEntity(), true, true)
            }
            val expectedResponse = ShowVehiclesResponse(vehicles = expectedVehicles, totalPages = (vehicles.size + pageSize - 1).div(pageSize), // округление вверх
                    totalElements = vehicles.size)
            val actualJson = mockMvc.get("/api/vehicles") {
                param("pageSize", pageSize.toString())
                param("pageNumber", pageNumber.toString())
            }.andExpect {
                status {
                    is2xxSuccessful()
                }
            }.andReturn().response.contentAsString
            val actual: ShowVehiclesResponse = Json.decodeFromString(actualJson)
            assertEquals(expectedResponse, actual)
        }
    }

    @Transactional
    @WithMockUser(username = mockedUserName, password = mockedUserPassword)
    @Test
    fun `sort by x desc`() {
        vehicleRepository.deleteAll()
        val vehicles = generateVehicles().sortedBy { it.id }.sortedByDescending { it.x }
        assert(vehicleRepository.count() == vehicles.size.toLong())
        getXData().forEach {
            val pageSize = it.first
            val pageNumber = it.second
            println("======TESTING FOR PAGE SIZE = $pageSize PAGE NUMBER = $pageNumber======")
            val expectedVehicles = vehicles.page(pageNumber, pageSize).map {
                objectAdapter.vehicleToResponse(it.toDomainEntity(), true, true)
            }
            val expectedResponse = ShowVehiclesResponse(vehicles = expectedVehicles, totalPages = (vehicles.size + pageSize - 1).div(pageSize), // округление вверх
                    totalElements = vehicles.size)
            val result = mockMvc.get("/api/vehicles") {
                param("pageSize", pageSize.toString())
                param("pageNumber", pageNumber.toString())
                param("sortingColumn", "X")
                param("ascending", "false")
            }.andExpect {
                status {
                    isOk()
                }
            }.andReturn()
            val response: ShowVehiclesResponse = Json.decodeFromString(result.response.contentAsString)
            assertEquals(expectedResponse.totalElements, response.totalElements)
            assertEquals(expectedResponse.totalPages, response.totalPages)
            for (i in (0 until expectedResponse.vehicles.size - 1)) {
                assertTrue(expectedResponse.vehicles[i].coordinates.x >= expectedResponse.vehicles[i + 1].coordinates.x)
            }
        }
    }

    @BeforeEach
    fun `insert mock user`() {
        vehicleRepository.deleteAll()
        userRepository.deleteAll()
        mockedUser = userRepository.save(mockedUser)
    }

    @AfterEach()
    fun cleanUp() {
        vehicleRepository.deleteAll()
        userRepository.deleteAll()
    }

    private fun generateVehicles(owner: UserJpaEntity = mockedUser, number: Int = 1000): List<VehicleJpaEntity> {
        val random = Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
        val vehicles = mutableListOf<VehicleJpaEntity>()
        repeat(number) {
            vehicles.add(this.createRandomVehicle(owner, random))
        }
        return vehicles
    }

    @AfterEach
    fun `clean up mock user`() {
        userRepository.delete(mockedUser)
    }

    companion object {
        @JvmStatic
        fun getXData(): List<Pair<Int, Int>> {
            return listOf(1, 10, 20, 25, 30, 40, 50).map { pageSize ->
                (0..(1000.div(pageSize)) step (1000.div(pageSize).div(5))).map { pageNumber ->
                    Pair(pageSize, pageNumber)
                }
            }.flatten()
        }
    }
}

internal fun <T> List<T>.page(pageNumber: Int, pageSize: Int): List<T> {
    return this.subList(pageNumber * pageSize, min((pageNumber + 1) * pageSize, this.size))
}