package lab9.backend.vehicle

import lab9.backend.entities.Vehicle
import lab9.backend.exceptions.ResourceNotFoundException
import lab9.backend.exceptions.UserNotAuthenticatedException
import lab9.backend.logger.KCoolLogger
import lab9.backend.users.UserRepository
import lab9.common.dto.VehicleDTO
import lab9.common.requests.VehicleFilter
import lab9.common.requests.VehicleSorting
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class VehicleServiceImpl(
    private val vehicleRepository: VehicleRepository,
    private val userRepository: UserRepository,
) : VehicleService {
    private val logger by KCoolLogger()

    override fun findAllWithPage(
        pageSize: Int,
        pageNumber: Int,
        filter: VehicleFilter?,
        sorting: VehicleSorting
    ): Page<Vehicle> {
        logger.info("Getting vehicle with pageSize=${pageSize}, pageNumber = $pageNumber, sorting=$sorting, filter=$filter")
        val page = PageRequest.of(
            pageNumber, pageSize, if (sorting.asc) {
                Sort.Direction.ASC
            } else {
                Sort.Direction.DESC
            }, VehicleDTO.getColumnName(sorting.column.ordinal)
        )
        logger.info("Page reqeust is $page")
        if (filter == null) {
            return vehicleRepository.findAll(page)
        }
        when (filter) {
            is VehicleFilter.IDFilter -> {
                return vehicleRepository.findAllByIdBetween(page, filter.lowerBound, filter.upperBound)
            }
        }
    }

    override fun findById(id: Int): Vehicle {
        return vehicleRepository.findFirstById(id)
            ?: throw ResourceNotFoundException("vehicle with id ${id} does not exists")
    }

    override fun create(vehicle: Vehicle, author: Principal): Vehicle {
        val authorUser = userRepository.findByUsername(author.name)
            ?: throw UserNotAuthenticatedException("no user with username ${author.name} found")

        return vehicleRepository.save(
            vehicle.copy(creator = authorUser)
        )
    }
}