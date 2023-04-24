package lab9.backend.vehicle

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab9.backend.BackendApplication
import lab9.backend.entities.Coordinates
import lab9.backend.entities.Vehicle
import lab9.backend.users.UserRepository
import lab9.backend.users.UserService
import lab9.common.dto.VehicleColumn
import lab9.common.requests.VehicleFilter
import lab9.common.responses.ShowVehiclesResponse
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.LocalDate
import kotlin.random.Random

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class],
)
@AutoConfigureMockMvc
@TestPropertySource(
    locations = ["classpath:application-it.properties"]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class VehicleControllerIntegrationTest(
    @Autowired
    private val mvc: MockMvc,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val vehicleRepository: VehicleRepository,
    @Autowired
    private val userService: UserService,
    @Autowired
    private val objectMapper: ObjectMapper,
) {
    @BeforeAll
    fun setup() {
        userService.createUser("test", "test")
    }

    val postgres = GenericContainer(DockerImageName.parse("postgresql"))
        .withExposedPorts(5432)

    @Nested
    inner class GetVehiclesTest {
        @Test
        fun `unauthorized`() {
            mvc.get("/api/vehicles")
                .andExpect {
                    this.status {
                        this.is4xxClientError()
                    }
                }
        }

        @Test
        @WithMockUser(username = "test", password = "test")
        fun `no vehicles`() {
            mvc.get("/api/vehicles")
                .andExpect {
                    this.status {
                        this.is2xxSuccessful()
                    }
                    this.content {
                        this.contentType(MediaType.APPLICATION_JSON)
                        this.json(
                            """{
                            |"vehicles": [],
                            |"totalPages": 0,
                            |"totalElements": 0
                            |}""".trimMargin()
                        )
                    }
                }
        }

        @Test
        @WithMockUser(username = "test", password = "test")
        fun `1 vehicle`() {
            var vehicle = Vehicle(
                -1,
                "1",
                userRepository.findByUsername("test")!!,
                Coordinates(1, 1),
                LocalDate.now(),
                1.0,
                VehicleType.BICYCLE,
                FuelType.ANTIMATTER
            )
            vehicleRepository.save(vehicle)
            vehicle = vehicleRepository.findAll()[0]
            val expected = ShowVehiclesResponse(
                totalElements = 1,
                totalPages = 1,
                vehicles = arrayOf(vehicle.toShowVehicleResponse()),
            )

            val expectedString = Json.encodeToString(expected)
            println("EXPECTED STRING: $expectedString")

            mvc.get("/api/vehicles")
                .andDo { print() }
                .andExpect {
                    status {
                        is2xxSuccessful()
                    }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(expectedString)
                    }
                }

            vehicleRepository.delete(vehicle)
        }

        @Test
        @WithMockUser(username = "test", password = "test")
        fun `10 vehicles on page`() {
            val user = userRepository.findByUsername("test")
            assert(user != null)
            val vehicles = mutableListOf<Vehicle>()
            val vehiclesNumber = 10
            for (i in 1..vehiclesNumber) {
                val newVehicle = Vehicle(
                    i,
                    i.toString(),
                    user,
                    coordinates = Coordinates(i, i.toLong()),
                    LocalDate.now(),
                    i * 1.0,
                    VehicleType.BICYCLE,
                    FuelType.ANTIMATTER,
                )
                vehicles.add(vehicleRepository.save(newVehicle))
            }

            val expectedResponse = ShowVehiclesResponse(
                vehicles.map { it.toShowVehicleResponse() }.toTypedArray(),
                1,
                10,
            )
            val expectedString = Json.encodeToString(expectedResponse)

            mvc.get("/api/vehicles")
                .andExpect {
                    status {
                        is2xxSuccessful()
                    }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(expectedString)
                    }
                }

            vehicles.forEach {
                vehicleRepository.delete(it)
            }
        }

        @Test
        @WithMockUser(username = "test", password = "test")
        fun `test sort by id descending`() {
            val user = userRepository.findByUsername("test")
            assert(user != null)
            val vehicles = mutableListOf<Vehicle>()
            val vehiclesNumber = 10
            val random = Random(123)
            for (i in 1..vehiclesNumber) {
                val newVehicle = Vehicle(
                    random.nextInt(),
                    i.toString(),
                    user,
                    coordinates = Coordinates(i, i.toLong()),
                    LocalDate.now(),
                    i * 1.0,
                    VehicleType.BICYCLE,
                    FuelType.ANTIMATTER,
                )
                vehicles.add(vehicleRepository.save(newVehicle))
            }
            vehicles.sortByDescending { it.id }

            val expectedResponse = ShowVehiclesResponse(
                vehicles.map { it.toShowVehicleResponse() }.toTypedArray(),
                1,
                10,
            )
            val expectedString = Json.encodeToString(expectedResponse)

            mvc.get("/api/vehicles") {
                param("column", "id")
                param("ass", "false")
            }
                .andExpect {
                    status {
                        is2xxSuccessful()
                    }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(expectedString)
                    }
                }

            vehicles.forEach {
                vehicleRepository.delete(it)
            }
        }

        @Test
        @WithMockUser(username = "test", password = "test")
        fun `test filter x`() {
            val user = userRepository.findByUsername("test")
            assert(user != null)
            val vehicles = mutableListOf<Vehicle>()
            val vehiclesNumber = 10
            val random = Random(123)
            for (i in 1..vehiclesNumber) {
                val newVehicle = Vehicle(
                    random.nextInt(),
                    i.toString(),
                    user,
                    coordinates = Coordinates(i, i.toLong()),
                    LocalDate.now(),
                    i * 1.0,
                    VehicleType.BICYCLE,
                    FuelType.ANTIMATTER,
                )
                vehicles.add(vehicleRepository.save(newVehicle))
            }
            vehicles.sortByDescending { it.id }

            val xLowerBound = 5
            val xUpperBound = 10
            val expectedResponse = ShowVehiclesResponse(
                vehicles
                    .filter { it.coordinates.x in (xLowerBound..xUpperBound) }
                    .map { it.toShowVehicleResponse() }
                    .toTypedArray(),
                1,
                10,
            )
            val expectedString = Json.encodeToString(expectedResponse)
            val filter = VehicleFilter.XFilter(xLowerBound, xUpperBound)
            val filterJson = Json.encodeToString<VehicleFilter>(filter)

            mvc.get("/api/vehicles") {
                param("filter", filterJson)
            }
                .andExpect {
                    status {
                        is2xxSuccessful()
                    }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(expectedString)
                    }
                }

            vehicles.forEach {
                vehicleRepository.delete(it)
            }
        }

    }

//    companion object {
//        @JvmStatic
//        @BeforeAll
//        fun setup(vehicleControllerIntegrationTest: VehicleControllerIntegrationTest): Unit {
//            vehicleControllerIntegrationTest.userService.createUser("test", "test")
//        }
//    }
}