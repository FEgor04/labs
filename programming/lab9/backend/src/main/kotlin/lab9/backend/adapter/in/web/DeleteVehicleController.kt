package lab9.backend.adapter.`in`.web

import lab9.backend.application.port.`in`.vehicles.DeleteVehicleUseCase
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/vehicles")
class DeleteVehicleController(
    val deleteVehicleUseCase: DeleteVehicleUseCase
) {
    @DeleteMapping("/{id}")
    fun deleteVehicle(@PathVariable id: Int, principal: Principal) {

    }
}