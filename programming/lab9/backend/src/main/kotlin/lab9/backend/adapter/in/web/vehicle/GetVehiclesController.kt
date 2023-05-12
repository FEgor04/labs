package lab9.backend.adapter.`in`.web.vehicle

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.adapter.`in`.web.dto.GetVehiclesRequest
import lab9.backend.adapter.`in`.web.dto.GetVehiclesSorting
import lab9.backend.adapter.`in`.web.dto.ShowVehicleResponse
import lab9.backend.adapter.`in`.web.dto.ShowVehiclesResponse
import lab9.backend.application.port.`in`.authorities.CheckAuthoritiesUseCase
import lab9.backend.application.port.`in`.authorities.GetUserAuthoritiesUseCase
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.user.PermissionDeniedException
import lab9.backend.application.port.`in`.vehicles.GetVehiclesUseCase
import lab9.backend.application.port.`in`.vehicles.VehicleNotFoundException
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle
import lab9.backend.logger.KCoolLogger
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/api/vehicles")
@Validated
class GetVehiclesController(
        private val getVehiclesUseCase: GetVehiclesUseCase,
        private val getUserAuthoritiesUseCase: GetUserAuthoritiesUseCase,
        private val getUserUseCase: GetUserUseCase,
        private val objectAdapter: WebObjectAdapter,
        private val checkUserAuthoritiesUseCase: CheckAuthoritiesUseCase,
) {
    private val logger by KCoolLogger()

    @GetMapping("/{id}")
    fun getSingleVehicle(
            @PathVariable id: Int,
            principal: Principal,
    ): ShowVehicleResponse {
        val user = getUserUseCase.getUserByUsername(principal.name) ?: throw PermissionDeniedException()
        val vehicle = getVehiclesUseCase.getVehicleById(Vehicle.VehicleID(id)) ?: throw VehicleNotFoundException()
        return objectAdapter.vehicleToResponse(
                vehicle,
                canEdit = checkUserAuthoritiesUseCase.checkUserCanEditVehicle(userID = user.id, vehicleID = vehicle.id),
                canDelete = checkUserAuthoritiesUseCase.checkUserCanDeleteVehicle(userID = user.id, vehicleID = vehicle.id),

                )
    }

    @GetMapping("")
    fun getVehicles(
            @RequestParam(defaultValue = "10") @Min(0) @Max(100) pageSize: Int,
            @RequestParam(defaultValue = "0") @Min(0) pageNumber: Int,
            @RequestParam(defaultValue = "ID") sortingColumn: String,
            @RequestParam(defaultValue = "true") ascending: Boolean,
            principal: Principal,
    ): ResponseEntity<ShowVehiclesResponse> {
        val sorting = GetVehiclesSorting(sortingColumn, ascending)
        logger.info("Handling GET /api/vehicles with pageSize = $pageSize, pageNumber=$pageNumber, sorting = $sorting")
        val request = GetVehiclesRequest(pageSize = pageSize, pageNumber = pageNumber, sorting = sorting)
        val vehicles = getVehiclesUseCase.getVehicles(
                objectAdapter.showVehiclesRequestToQuery(request)
        )
        val user = getUserUseCase.getUserByUsername(principal.name)
        if (user == null) {
            logger.warn("Could not get user from principal. Username: ${principal.name}")
            return ResponseEntity.badRequest().build();
        }
        val principalEditAuthorities = getUserAuthoritiesUseCase.getUserAuthoritiesToEdit(user.id)
        val principalDeleteAuthorities = getUserAuthoritiesUseCase.getUserAuthoritiesToDelete(user.id)
        val response = ShowVehiclesResponse(
                vehicles = vehicles.content.map {
                    objectAdapter.vehicleToResponse(
                            it,
                            canEdit = it.creatorID in principalEditAuthorities || it.creatorID == user.id,
                            canDelete = it.creatorID in principalDeleteAuthorities || it.creatorID == user.id,
                    )
                },
                totalPages = vehicles.totalPages,
                totalElements = vehicles.totalElements
        )
        return ResponseEntity.ok(
                response
        )
    }
}