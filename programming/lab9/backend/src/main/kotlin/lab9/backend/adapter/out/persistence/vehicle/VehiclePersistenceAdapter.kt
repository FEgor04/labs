package lab9.backend.adapter.out.persistence.vehicle

import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.application.port.`in`.vehicles.GetVehiclesResponse
import lab9.backend.application.port.`in`.vehicles.VehicleAlreadyExistsException
import lab9.backend.application.port.out.vehicle.CreateVehiclePort
import lab9.backend.application.port.out.vehicle.DeleteVehiclePort
import lab9.backend.application.port.out.vehicle.GetVehiclesPort
import lab9.backend.common.PersistenceAdapter
import lab9.backend.domain.Vehicle
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.LocalDate

@PersistenceAdapter
class VehiclePersistenceAdapter(
    private val vehicleRepository: VehicleRepository,
) : GetVehiclesPort, CreateVehiclePort, DeleteVehiclePort {
    override fun getVehicles(query: GetVehiclesQuery): GetVehiclesResponse {
        val sortDirection: Sort.Direction = if (query.sorting.asc) {
            Sort.Direction.ASC
        } else {
            Sort.Direction.DESC
        }
        val page = vehicleRepository.findAll(
            PageRequest.of(
                query.pageNumber,
                query.pageSize,
                Sort.by(sortDirection, vehicleFieldToJpaColumn(query.sorting.field))
            )
        )
        return GetVehiclesResponse(
            vehicles = page.content.map { it.toDomainEntity() }.toList(),
            totalElements = page.totalElements.toInt(),
            totalPages = page.totalPages
        )
    }

    private fun vehicleFieldToJpaColumn(field: Vehicle.Field): String {
        return when(field) {
            Vehicle.Field.ID -> "id"
            Vehicle.Field.NAME -> "name"
            Vehicle.Field.X -> "x"
            Vehicle.Field.Y -> "y"
            Vehicle.Field.CREATION_DATE -> "creationDate"
            Vehicle.Field.ENGINE_POWER -> "enginePower"
            Vehicle.Field.VEHICLE_TYPE -> "vehicleType"
            Vehicle.Field.FUEL_TYPE -> "fuelType"
            Vehicle.Field.CREATOR_ID -> "creator_id"
        }
    }

    override fun getVehicle(id: Vehicle.VehicleID): Vehicle? {
        val vehicle = vehicleRepository.findFirstById(id.id)
        return vehicle?.toDomainEntity()
    }

    override fun createVehicle(vehicle: Vehicle): Vehicle {
        return try {
            vehicleRepository.saveAndFlush(
                VehicleJpaEntity(
                    id = null,
                    vehicle.name,
                    UserJpaEntity(vehicle.creatorID.id, "", "", emptySet(), emptySet(), emptySet()),
                    creationDate = LocalDate.now(),
                    x = vehicle.coordinates.x,
                    y = vehicle.coordinates.y,
                    enginePower = vehicle.enginePower,
                    vehicleType = vehicle.vehicleType,
                    fuelType = vehicle.fuelType
                )
            ).toDomainEntity()
        } catch (e: DataIntegrityViolationException) {
            throw VehicleAlreadyExistsException()
        }
    }

    override fun delete(vehicleID: Vehicle.VehicleID) {
        return vehicleRepository.deleteById(vehicleID.id)
    }

}