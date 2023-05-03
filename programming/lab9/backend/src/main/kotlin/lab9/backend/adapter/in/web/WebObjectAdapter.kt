package lab9.backend.adapter.`in`.web

import jakarta.validation.Valid
import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.domain.Vehicle
import lab9.common.dto.CoordinatesDTO
import lab9.common.requests.ShowVehiclesRequest
import lab9.common.responses.ShowVehicleResponse
import lab9.common.responses.ShowVehiclesResponse
import org.springframework.stereotype.Component

@Component
class WebObjectAdapter {
    fun vehicleToResponse(vehicle: Vehicle): ShowVehicleResponse {
        return ShowVehicleResponse(
            vehicle.id.id,
            vehicle.name,
            coordinates = CoordinatesDTO(vehicle.coordinates.x, vehicle.coordinates.y),
            creationDate = vehicle.creationDate.toString(),
            vehicleType = vehicle.vehicleType,
            fuelType = vehicle.fuelType,
            creatorId = vehicle.creatorID.id,
            enginePower = vehicle.enginePower,
            canEdit = false,
            canDelete = false,
        )
    }

    fun showVehiclesRequestToQuery(request: GetVehiclesRequest): GetVehiclesQuery {
        return GetVehiclesQuery(request.pageNumber, request.pageSize)
    }
}