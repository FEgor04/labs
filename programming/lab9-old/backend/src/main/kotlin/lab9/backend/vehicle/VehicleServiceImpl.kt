package lab9.backend.vehicle

import lab9.backend.entities.Vehicle
import lab9.backend.exceptions.ResourceNotFoundException
import lab9.backend.exceptions.UserNotAuthenticatedException
import lab9.backend.users.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class VehicleServiceImpl(
    private val vehicleRepository: VehicleRepository,
    private val userRepository: UserRepository,
): VehicleService {
    override fun findAllWithPage(pageSize: Int, pageNumber: Int): List<Vehicle> {
        return vehicleRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNumber)).content
    }

    override fun findById(id: Int): Vehicle {
        return vehicleRepository.findFirstById(id) ?: throw ResourceNotFoundException("vehicle with id ${id} does not exists")
    }

    override fun create(vehicle: Vehicle, author: Principal): Vehicle {
        val authorUser = userRepository.findByUsername(author.name)
            ?: throw UserNotAuthenticatedException("no user with username ${author.name} found")

        return vehicleRepository.save(
            vehicle.copy(creator = authorUser)
        )
    }
}