package lab9.backend.adapter.`in`.web.vehicle

import jakarta.validation.Valid
import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.adapter.`in`.web.dto.CreateVehicleRequest
import lab9.backend.adapter.`in`.web.dto.ShowVehicleResponse
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.vehicles.CreateVehicleUseCase
import lab9.backend.application.port.`in`.vehicles.VehicleAlreadyExistsException
import lab9.backend.domain.Vehicle
import lab9.backend.logger.KCoolLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/vehicles")
class CreateVehicleController(
    private val createVehicleUseCase: CreateVehicleUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val objectAdapter: WebObjectAdapter,
) {
    private val logger by KCoolLogger()

    @PostMapping("")
    fun createVehicle(
        @Valid @RequestBody request: CreateVehicleRequest,
        principal: Principal
    ): ResponseEntity<ShowVehicleResponse> {
        logger.info("Handling POST /api/vehicles request. Vehicle name: ${request.name}")
        val user = getUserUseCase.getUserByUsername(principal.name) ?: return ResponseEntity.status(401).build()
        logger.info("User is ${user.username}#${user.id}")
        val newVehicle = createVehicleUseCase.create(objectAdapter.createVehicleRequestToQuery(request, user.id))
        logger.info("Successfully created new vehicle")
        return ResponseEntity.ok(objectAdapter.vehicleToResponse(newVehicle).copy(canEdit = true, canDelete = true))
    }
}