package lab9.backend.adapter.`in`.web

import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleJpaEntity
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository
import lab9.backend.domain.Vehicle
import org.junit.ClassRule
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.random.Random

@ContextConfiguration(initializers = [PostgresIntegrationTest.Initializer::class])
open class PostgresIntegrationTest(
        protected open val userRepository: UserRepository,
        protected open val vehicleRepository: VehicleRepository,
) {
    fun createNewUser(username: String, password: String): UserJpaEntity {
        return userRepository.saveAndFlush(UserJpaEntity(null, username, password, emptySet(), emptySet(), emptySet()))
    }

    fun createRandomVehicle(owner: UserJpaEntity, random: Random = Random(Instant.now().epochSecond)): VehicleJpaEntity {
        return vehicleRepository.saveAndFlush(
                VehicleJpaEntity(
                        null,
                        name = (random.nextInt() * random.nextDouble() / random.nextFloat()).toString(),
                        x = random.nextInt(-500, Int.MAX_VALUE),
                        y = random.nextLongOrNull(),
                        creationDate = random.nextLocalDate(),
                        enginePower = random.nextDouble(0.00001, Double.MAX_VALUE),
                        vehicleType = Vehicle.VehicleType.values().toList().randomElement(random),
                        fuelType = Vehicle.FuelType.values().toList().randomElementOrNull(random),
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

        val kafkaContainer: KafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"))


        init {
            postgresContainer.start()
            kafkaContainer.start()
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
                    "spring.kafka.bootstrap-servers=${kafkaContainer.bootstrapServers}",
//                "spring.jpa.show-sql=true",
//                "spring.jpa.properties.hibernate.format_sql=true"
            ).applyTo(applicationContext.environment);
        }
    }
}

internal fun <T> List<T>.randomElement(random: Random): T {
    return this[random.nextInt(0, this.size)]
}

internal fun <T> List<T>.randomElementOrNull(random: Random): T? {
    val ind = random.nextInt(0, this.size + 1)
    if (ind == this.size) {
        return null
    }
    return this[ind]
}


internal fun Random.nextLongOrNull(nullChance: Double = 0.1, from: Long = Long.MIN_VALUE, until: Long = Long.MAX_VALUE): Long? {
    val nullRandom = this.nextDouble()
    if (nullRandom > nullChance) {
        return null
    }
    return nextLong(from, until)
}

internal fun Random.nextLocalDate(): LocalDate {
    return LocalDate.ofInstant(Instant.ofEpochSecond(this.nextInt().toLong()), ZoneId.systemDefault())
}