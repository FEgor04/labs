package lab9.backend.adapter.`in`.web.user

import lab9.backend.adapter.`in`.web.PostgresIntegrationTest
import lab9.backend.adapter.out.persistence.authorities.AuthoritiesRepository
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.adapter.out.persistence.vehicle.VehicleRepository

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GrantAuthoritiesControllerIntegrationTest(
    @Autowired
    override val userRepository: UserRepository,
    @Autowired
    override val vehicleRepository: VehicleRepository,
    @Autowired
    val authoritiesRepository: AuthoritiesRepository,
) : PostgresIntegrationTest(userRepository, vehicleRepository) {
}