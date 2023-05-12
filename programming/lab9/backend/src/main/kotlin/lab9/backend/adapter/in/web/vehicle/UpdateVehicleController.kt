package lab9.backend.adapter.`in`.web.vehicle

import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.adapter.`in`.web.dto.CreateVehicleRequest
import lab9.backend.adapter.`in`.web.dto.ShowVehicleResponse
import lab9.backend.application.port.`in`.authorities.CheckAuthoritiesUseCase
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.user.PermissionDeniedException
import lab9.backend.application.port.`in`.vehicles.UpdateVehicleQuery
import lab9.backend.application.port.`in`.vehicles.UpdateVehicleUseCase
import lab9.backend.domain.Vehicle
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api")
class UpdateVehicleController(
        private val updateVehicleUseCase: UpdateVehicleUseCase,
        private val getUserUseCase: GetUserUseCase,
        private val checkAuthoritiesUseCase: CheckAuthoritiesUseCase,
        private val webObjectAdapter: WebObjectAdapter,
) {
    @PutMapping("/vehicles/{id}")
    fun updateVehicle(
            @PathVariable id: Int,
            @RequestBody request: CreateVehicleRequest,
            principal: Principal
    ): ShowVehicleResponse {
        val user = getUserUseCase.getUserByUsername(principal.name) ?: throw PermissionDeniedException()
        val newVehicle = updateVehicleUseCase.updateVehicle(
                UpdateVehicleQuery(
                        Vehicle.VehicleID(id),
                        name = request.name,
                        coordinates = Vehicle.Coordinates(request.x, request.y),
                        enginePower = request.enginePower,
                        vehicleType = request.vehicleType,
                        fuelType = request.fuelType,
                        actorID = user.id,
                )
        )
        return webObjectAdapter.vehicleToResponse(
                newVehicle,
                canEdit = checkAuthoritiesUseCase.checkUserCanEditVehicle(userID = user.id, vehicleID = newVehicle.id),
                canDelete = checkAuthoritiesUseCase.checkUserCanDeleteVehicle(userID = user.id, vehicleID = newVehicle.id),
        )
    }
}