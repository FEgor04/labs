package lab9.backend.adapter.`in`.web.vehicle

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.application.port.`in`.authorities.GetUserAuthoritiesUseCase
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.vehicles.GetVehiclesUseCase
import lab9.backend.domain.User
import lab9.backend.logger.KCoolLogger
import lab9.common.responses.ShowVehiclesResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@Validated
@RequestMapping("/api/vehicles")
class GetVehiclesController(
    private val getVehiclesUseCase: GetVehiclesUseCase,
    private val getUserAuthoritiesUseCase: GetUserAuthoritiesUseCase,
    // TODO : искать authorities пользователя по username
    private val getUserUseCase: GetUserUseCase,
    private val objectAdapter: WebObjectAdapter,
) {
    private val logger by KCoolLogger()
    @GetMapping("")
    fun getVehicles(
        @RequestParam(defaultValue = "10") @Min(0) @Max(100) pageSize: Int,
        @RequestParam(defaultValue = "0") @Min(0) pageNumber: Int,
        principal: Principal,
    ): ResponseEntity<ShowVehiclesResponse> {
        logger.info("Handling GET /api/vehicles with pageSize = $pageSize and pageNumber=$pageNumber")
        val request = GetVehiclesRequest(pageSize = pageSize, pageNumber = pageNumber)
        val vehicles = getVehiclesUseCase.getVehicles(
            objectAdapter.showVehiclesRequestToQuery(request)
        )
        val user = getUserUseCase.getUserByUsername(principal.name)
        if (user == null) {
            logger.warn("Could not get user from principal. Username: ${principal.name}")
            return ResponseEntity.badRequest().build();
        }
        val response = ShowVehiclesResponse(
            vehicles.vehicles.map { objectAdapter.vehicleToResponse(it) }.toTypedArray(),
            totalPages = vehicles.totalPages,
            totalElements = vehicles.totalElements
        )
        val principalEditAuthorities = getUserAuthoritiesUseCase.getUserAuthoritiesToEdit(user.id)
        val principalDeleteAuthorities = getUserAuthoritiesUseCase.getUserAuthoritiesToEdit(user.id)
        response.vehicles.map { vehicle ->
            vehicle.copy(
                canEdit = principalEditAuthorities.contains(User.UserID(vehicle.creatorId)) || user.id.id == vehicle.creatorId,
                canDelete = principalDeleteAuthorities.contains(User.UserID(vehicle.creatorId)) || user.id.id == vehicle.creatorId,
            )
        }
        return ResponseEntity.ok(response)
    }
}