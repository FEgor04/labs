package lab9.backend.adapter.`in`.web

import lab9.backend.adapter.`in`.web.dto.*
import lab9.backend.application.port.`in`.vehicles.CreateVehicleQuery
import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.application.port.`in`.vehicles.PagedResponse
import lab9.backend.common.ObjectAdapter
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle

@ObjectAdapter
class WebObjectAdapter {
    fun vehicleToResponse(vehicle: Vehicle, canEdit: Boolean, canDelete: Boolean): ShowVehicleResponse {
        return ShowVehicleResponse(
                vehicle.id.id,
                vehicle.name,
                coordinates = CoordinatesDTO(vehicle.coordinates.x, vehicle.coordinates.y),
                creationDate = vehicle.creationDate.toString(),
                vehicleType = vehicle.vehicleType,
                fuelType = vehicle.fuelType,
                creatorId = vehicle.creatorID.id,
                enginePower = vehicle.enginePower,
                canEdit = canEdit,
                canDelete = canDelete,
        )
    }

    fun usersToResponse(
            users: PagedResponse<User>,
            canYouEditResolver: (User.UserID) -> Boolean,
            canYouDeleteResolver: (User.UserID) -> Boolean,
            canHeEditResolver: (User.UserID) -> Boolean,
            canHeDeleteResolver: (User.UserID) -> Boolean
    ): GetUsersResponse {
        return GetUsersResponse(
                totalPages = users.totalPages,
                totalElements = users.totalElements,
                content = users.content.map {
                    userToShowUserResponse(it,
                            canYouEdit = canYouEditResolver(it.id),
                            canYouDelete = canYouDeleteResolver(it.id),
                            canHeDelete = canHeDeleteResolver(it.id),
                            canHeEdit = canHeEditResolver(it.id)
                    )
                }
        )
    }

    fun sortingRequestToQuery(sorting: GetVehiclesSorting): GetVehiclesQuery.Sorting {
        return GetVehiclesQuery.Sorting(
                Vehicle.Field.valueOf(sorting.sortingColumn.uppercase()),
                sorting.ascending,
        )
    }

    fun showVehiclesRequestToQuery(request: GetVehiclesRequest): GetVehiclesQuery {
        return GetVehiclesQuery(request.pageNumber, request.pageSize, sortingRequestToQuery(request.sorting))
    }

    fun createVehicleRequestToQuery(request: CreateVehicleRequest, creatorId: User.UserID): CreateVehicleQuery {
        return CreateVehicleQuery(
                request.name,
                coordinates = Vehicle.Coordinates(request.x, request.y),
                creatorID = creatorId,
                enginePower = request.enginePower,
                vehicleType = request.vehicleType,
                fuelType = request.fuelType
        )
    }

    fun userToShowUserResponse(user: User, canYouEdit: Boolean, canYouDelete: Boolean, canHeEdit: Boolean, canHeDelete: Boolean): ShowUserResponse {
        return ShowUserResponse(
                id = user.id.id,
                username = user.username,
                canYouEdit = canYouEdit,
                canYouDelete = canYouDelete,
                canHeDelete = canHeDelete, canHeEdit = canHeEdit
        )
    }
}