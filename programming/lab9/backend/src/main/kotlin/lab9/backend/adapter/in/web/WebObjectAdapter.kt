package lab9.backend.adapter.`in`.web

import lab9.backend.adapter.`in`.web.dto.*
import lab9.backend.application.port.`in`.vehicles.CreateVehicleQuery
import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.common.ObjectAdapter
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle

@ObjectAdapter
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

    fun createVehicleRequestToQuery(request: CreateVehicleRequest, creatorId: User.UserID) : CreateVehicleQuery {
        return CreateVehicleQuery(
            request.name,
            coordinates = Vehicle.Coordinates(request.x, request.y),
            creatorID = creatorId,
            enginePower = request.enginePower,
            vehicleType = request.vehicleType,
            fuelType = request.fuelType
        )
    }

    fun userToShowUserResponse(user: User): ShowUserResponse {
        return ShowUserResponse(
            id = user.id.id,
            username = user.username
        )
    }
}