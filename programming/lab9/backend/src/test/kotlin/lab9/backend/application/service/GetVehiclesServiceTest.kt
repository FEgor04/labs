package lab9.backend.application.service

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import lab9.backend.BackendApplication
import lab9.backend.adapter.`in`.web.PostgresIntegrationTest
import lab9.backend.adapter.`in`.web.dto.GetVehiclesFilterDTO
import lab9.backend.adapter.`in`.web.vehicle.GetVehiclesControllerIntegrationTest
import lab9.backend.adapter.`in`.web.vehicle.page
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.application.port.`in`.vehicles.PagedResponse
import lab9.backend.domain.Vehicle
import org.junit.jupiter.api.AfterAll

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant
import java.time.LocalDate
import kotlin.math.pow
import kotlin.random.Random

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [BackendApplication::class]
)
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetVehiclesServiceTest(
    @Autowired
    override val userRepository: UserRepository,
    @Autowired
    override val vehicleRepository: VehicleRepository,
    @Autowired
    private val getVehiclesService: GetVehiclesService,
) : PostgresIntegrationTest(
    userRepository, vehicleRepository
) {
    private final val vehiclesCount = 1000
    val random = Random(Instant.now().epochSecond)
    val owner = createNewUser("test", "test")
    val vehicles = (0 until vehiclesCount).map {
        createRandomVehicle(owner, random)
    }.map {
        it.toDomainEntity()
    }

    @Test
    fun `test filter by fuel type and sort by engine power`() {
        assertEquals(vehiclesCount.toLong(), vehicleRepository.count())
        for (withNull in listOf(true, false)) {
            for (values in Vehicle.FuelType.values()
                .toList()
                .toNullable()
                .toList()
                .allPossibleSubSets()
            ) {
                val filter = GetVehiclesQuery.Filter.EnumFilter<Vehicle.FuelType>(
                    values = values
                        .toMutableList()
                        .apply {
                            if (withNull) {
                                add(null)
                            }
                        },
                    field = Vehicle.Field.FUEL_TYPE,
                )
                val filteredVehicles = vehicles
                    .sortedBy { it.enginePower }
                    .filter { vehicle ->
                        filter.values.contains(vehicle.fuelType)
                    }
                GetVehiclesControllerIntegrationTest.Companion.getPagedData(totalElements = filteredVehicles.size)
                    .forEach {
                        val pageSize = it.first
                        val pageNumber = it.second
                        println("TESTING PAGE SIZE = $pageSize PAGE NUMBER = $pageNumber NULL? = $withNull")
                        val expectedResponse = PagedResponse<Vehicle>(
                            content = filteredVehicles.page(pageNumber, pageSize),
                            totalElements = filteredVehicles.size,
                            totalPages = (filteredVehicles.size + pageSize - 1).div(pageSize)
                        )
                        val query = GetVehiclesQuery(
                            pageNumber,
                            pageSize,
                            sorting = GetVehiclesQuery.Sorting(Vehicle.Field.ENGINE_POWER, true),
                            filter
                        )
                        val actualResponse = getVehiclesService.getVehicles(query)
                        assertEquals(
                            expectedResponse.totalElements,
                            actualResponse.totalElements,
                            "total elements should be ${expectedResponse.totalElements}, but was ${actualResponse.totalElements}"
                        )
                        assertEquals(
                            expectedResponse.totalPages,
                            actualResponse.totalPages,
                            "total pages should be ${expectedResponse.totalPages}, but was ${actualResponse.totalPages}"
                        )
                        assertEquals(
                            expectedResponse.content.size, actualResponse.content.size,
                            "total size should be ${expectedResponse.content.size} but was ${actualResponse.content.size}"
                        )
                        for (i in expectedResponse.content.indices) {
                            assertEquals(
                                expectedResponse.content[i], actualResponse.content[i], "" +
                                        "element number ${i} should be ${expectedResponse.content[i]} but was ${actualResponse.content[i]}"
                            )
                        }
                    }
            }
        }
    }

    @Test
    fun `test filter pattern name & sort engine power`() {
        val names: List<String> = listOf(
            vehicles[0].name,
            "1234",
            "SUCH ELEMENT DOES NOT EXIST",
            "" // all elements
        )
        for (name in names) {
            val filteredVehicles = vehicles
                .sortedBy { it.enginePower }
                .filter { Regex(".*$name.*").matches(it.name) }
            GetVehiclesControllerIntegrationTest.Companion.getPagedData(totalElements = filteredVehicles.size)
                .forEach {
                    val pageSize = it.first
                    val pageNumber = it.second
                    println("TESTING FOR PAGE SIZE = $pageSize AND PAGE NUMBER = $pageNumber. PATTERN: $name")
//                    println(filteredVehicles)
                    val expectedVehicles = filteredVehicles.page(pageNumber, pageSize)
                    val expectedResponse = PagedResponse<Vehicle>(
                        content = expectedVehicles,
                        totalPages = (filteredVehicles.size + pageSize - 1).div(pageSize),
                        totalElements = filteredVehicles.size
                    )
                    val actualResponse = getVehiclesService.getVehicles(
                        GetVehiclesQuery(
                            pageNumber = pageNumber,
                            pageSize = pageSize,
                            sorting = GetVehiclesQuery.Sorting(field = Vehicle.Field.ENGINE_POWER, asc = true),
                            filter = GetVehiclesQuery.Filter.PatternFilter(name, Vehicle.Field.NAME)
                        )
                    )
                    println(expectedResponse)
                    println(actualResponse)

                    assertEquals(expectedResponse, actualResponse)
                }
        }
    }

    @Test
    fun `test filter by x, sort by id`() {
        val lowerBound = vehicles.minBy { it.coordinates.x }.coordinates.x.toLong()
        val upperBound = random.nextLong(lowerBound + 1, lowerBound * 100)
        val filter =
            GetVehiclesFilterDTO.NumberFilter(Vehicle.Field.X, lowerBound = lowerBound, upperBound = upperBound)
                .toQueryFilter()
        val filteredVehicles = vehicles
            .sortedBy { it.id.id }
            .filter { it.coordinates.x in lowerBound..upperBound }
        GetVehiclesControllerIntegrationTest.Companion.getPagedData(totalElements = filteredVehicles.size)
            .forEach {
                val pageSize = it.first
                val pageNumber = it.second
                println("TESTING FOR PAGE SIZE = $pageSize AND PAGE NUMBER = $pageNumber. FILTER: [$lowerBound; $upperBound]")
                val expectedVehicles = filteredVehicles.page(pageNumber, pageSize)
                val expectedResponse = PagedResponse<Vehicle>(
                    content = expectedVehicles,
                    totalPages = (filteredVehicles.size + pageSize - 1).div(pageSize),
                    totalElements = filteredVehicles.size
                )
                val actualResponse = getVehiclesService.getVehicles(
                    GetVehiclesQuery(
                        pageNumber = pageNumber,
                        pageSize = pageSize,
                        sorting = GetVehiclesQuery.Sorting(field = Vehicle.Field.ID, asc = true),
                        filter = filter,
                    )
                )
                println(expectedResponse)
                println(actualResponse)

                assertEquals(expectedResponse, actualResponse)
            }
    }

    @Test
    fun `test filter by creation date, sort by id`() {
        val lowerBound = vehicles[10].creationDate
        val upperBound = vehicles[200].creationDate
        val filter: GetVehiclesQuery.Filter.BetweenFilter<LocalDate> =
            GetVehiclesFilterDTO.DateFilter(lowerBound, upperBound)
                .toQueryFilter() as GetVehiclesQuery.Filter.BetweenFilter<LocalDate>
        val filteredVehicles = vehicles
            .sortedBy { it.id.id }
            .filter {
                (lowerBound.isBefore(it.creationDate) || lowerBound.isEqual(it.creationDate)) &&
                        (it.creationDate.isBefore(
                            upperBound
                        ) || it.creationDate.isEqual(upperBound))
            }
        GetVehiclesControllerIntegrationTest.Companion.getPagedData(totalElements = filteredVehicles.size)
            .forEach {
                val pageSize = it.first
                val pageNumber = it.second
                println("TESTING FOR PAGE SIZE = $pageSize AND PAGE NUMBER = $pageNumber")
                val expectedVehicles = filteredVehicles.page(pageNumber, pageSize)
                val expectedResponse = PagedResponse<Vehicle>(
                    content = expectedVehicles,
                    totalPages = (filteredVehicles.size + pageSize - 1).div(pageSize),
                    totalElements = filteredVehicles.size
                )
                val actualResponse = getVehiclesService.getVehicles(
                    GetVehiclesQuery(
                        pageNumber = pageNumber,
                        pageSize = pageSize,
                        sorting = GetVehiclesQuery.Sorting(field = Vehicle.Field.ID, asc = true),
                        filter = filter,
                    )
                )
                assertEquals(expectedResponse, actualResponse)
            }
    }

    @AfterAll
    fun cleanUp() {
        vehicleRepository.deleteAll()
        userRepository.deleteAll()
    }
}

fun <T> List<T>.allPossibleSubSets(): List<List<T>> {
    return (0 until 2.0.pow(this.size.toDouble()).toInt()).map {
        this.filterIndexed { ind, _ ->
            it.and(1 shl ind) == 1
        }
    }
}

fun <T> Iterable<T>.toNullable(): Iterable<T?> {
    return map<T, T?> { it }
}