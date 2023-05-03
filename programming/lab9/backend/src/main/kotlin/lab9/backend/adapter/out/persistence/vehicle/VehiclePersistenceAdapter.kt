package lab9.backend.adapter.out.persistence.vehicle

import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.application.port.`in`.vehicles.GetVehiclesResponse
import lab9.backend.application.port.out.GetVehiclesPort
import lab9.backend.common.PersistenceAdapter
import org.springframework.data.domain.PageRequest

@PersistenceAdapter
class VehiclePersistenceAdapter(
    private val vehicleRepository: VehicleRepository,
): GetVehiclesPort {
    override fun getVehicles(query: GetVehiclesQuery): GetVehiclesResponse {
        val page = vehicleRepository.findAll(PageRequest.of(query.pageNumber, query.pageSize))
        return GetVehiclesResponse(
            vehicles = page.content.map { it.toDomainEntity() }.toList(),
            totalElements = page.totalElements.toInt(),
            totalPages = page.totalPages
        )
    }

}