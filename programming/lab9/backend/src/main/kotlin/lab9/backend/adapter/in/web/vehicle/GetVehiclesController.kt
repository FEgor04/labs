package lab9.backend.adapter.`in`.web.vehicle

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.adapter.`in`.web.dto.GetVehiclesRequest
import lab9.backend.adapter.`in`.web.dto.GetVehiclesSorting
import lab9.backend.adapter.`in`.web.dto.ShowVehiclesResponse
import lab9.backend.application.port.`in`.authorities.GetUserAuthoritiesUseCase
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.vehicles.GetVehiclesUseCase
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle
import lab9.backend.logger.KCoolLogger
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@Validated
@RequestMapping("/api/vehicles")
class GetVehiclesController(
    private val getVehiclesUseCase: GetVehiclesUseCase,
    private val getUserAuthoritiesUseCase: GetUserAuthoritiesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val objectAdapter: WebObjectAdapter,
) {
    private val logger by KCoolLogger()

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
        logger.info("Principal id id ${user.id.id}")
        val response = ShowVehiclesResponse(
            vehicles = vehicles.vehicles.map { objectAdapter.vehicleToResponse(it) },
            totalPages = vehicles.totalPages,
            totalElements = vehicles.totalElements
        )
        val principalEditAuthorities = getUserAuthoritiesUseCase.getUserAuthoritiesToEdit(user.id)
        val principalDeleteAuthorities = getUserAuthoritiesUseCase.getUserAuthoritiesToEdit(user.id)
        logger.info(
            "Principal edit authorities: ${
                principalEditAuthorities.map { it.id.toString() }
            }"
        )
        logger.info(
            "Principal delete authorities: ${
                principalDeleteAuthorities.map { it.id.toString() }
            }"
        )
        return ResponseEntity.ok(
            response.copy(
                vehicles = response.vehicles.map { vehicle ->
                    logger.info(vehicle.toString())
                    logger.info((vehicle.creatorId == user.id.id).toString())
                    vehicle.copy(
                        canEdit = principalEditAuthorities.contains(User.UserID(vehicle.creatorId)) || user.id.id == vehicle.creatorId,
                        canDelete = principalDeleteAuthorities.contains(User.UserID(vehicle.creatorId)) || user.id.id == vehicle.creatorId,
                    )
                }
            )
        )
    }
}