package lab9.backend.adapter.`in`.web.vehicle

import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.vehicles.DeleteVehicleUseCase
import lab9.backend.domain.Vehicle
import lab9.backend.logger.KCoolLogger
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/vehicles")
class DeleteVehicleController(
    private val deleteVehicleUseCase: DeleteVehicleUseCase,
    private val getUserUseCase: GetUserUseCase,
) {
    private val logger by KCoolLogger()
    @DeleteMapping("/{id}")
    fun deleteVehicle(@PathVariable id: Int, principal: Principal): ResponseEntity<*> {
        logger.info("Handling DELETE /api/vehicles/${id}")
        val user = getUserUseCase.getUserByUsername(principal.name) ?: throw Exception("wtf")
        deleteVehicleUseCase.deleteVehicle(vehicleID = Vehicle.VehicleID(id), user.id)
        return ResponseEntity.ok(null)
    }
}