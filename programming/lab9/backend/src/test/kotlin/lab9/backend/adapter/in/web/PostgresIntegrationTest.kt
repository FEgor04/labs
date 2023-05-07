package lab9.backend.adapter.`in`.web

import lab9.backend.BackendApplication
import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleJpaEntity
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import lab9.backend.domain.Vehicle
import org.junit.ClassRule
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import java.time.LocalDate
import kotlin.random.Random

@ContextConfiguration(initializers = [PostgresIntegrationTest.Initializer::class])
open class PostgresIntegrationTest(
    protected open val userRepository: UserRepository,
    protected open val vehicleRepository: VehicleRepository,
) {
    fun createNewUser(username: String, password: String): UserJpaEntity {
        return userRepository.saveAndFlush(UserJpaEntity(null, username, password, emptySet(), emptySet(), emptySet()))
    }

    fun createRandomVehicle(owner: UserJpaEntity): VehicleJpaEntity {
        return vehicleRepository.saveAndFlush(
            VehicleJpaEntity(
                null,
                name = Random.nextInt().toString(),
                x = 1,
                y = 1,
                creationDate = LocalDate.now(),
                enginePower = 1.0,
                vehicleType = Vehicle.VehicleType.BOAT,
                fuelType = Vehicle.FuelType.ANTIMATTER,
                creator = owner
            )
        )
    }

    companion object {
        @JvmField
        @ClassRule
        val postgresContainer = PostgreSQLContainer("postgres:13.3")
            .withDatabaseName("integration-tests")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432)!!

        init {
            postgresContainer.start()
        }

    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=${postgresContainer.jdbcUrl}",
                "spring.datasource.username=${postgresContainer.username}",
                "spring.datasource.password=${postgresContainer.password}",
                "spring.datasource-driver-class=${postgresContainer.driverClassName}",
                "spring.jpa.hibernate.ddl-auto=update",
                "spring.jpa.show-sql=true",
                "spring.jpa.properties.hibernate.format_sql=true"
            ).applyTo(applicationContext.environment);
        }
    }
}