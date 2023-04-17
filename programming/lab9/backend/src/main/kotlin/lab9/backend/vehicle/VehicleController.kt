package lab9.backend.vehicle

import lab9.backend.entities.Coordinates
import lab9.backend.entities.User
import lab9.backend.entities.Vehicle
import lab9.backend.logger.KCoolLogger
import lab9.common.requests.CreateVehicleRequest
import lab9.common.responses.ShowVehicleResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.time.LocalDate

@RestController
@RequestMapping("/api/vehicles")
class VehicleController(
    private val vehicleService: VehicleService,
) {
    companion object {
        private val logger by KCoolLogger()
    }

    @GetMapping("")
    fun getVehicles(
        @RequestParam(required = true, defaultValue = "10")
        pageSize: Int,
        @RequestParam(required = true, defaultValue = "1")
        pageNumber: Int,
    ): List<ShowVehicleResponse> {
        logger.info("Handling GET request at /. pageSize: $pageSize, pageNumber: $pageNumber")
        val vehicles = vehicleService.findAllWithPage(pageSize, pageNumber)
        return vehicles.map { it.toShowVehicleResponse() }
    }

    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable id: Int): ShowVehicleResponse {
        return vehicleService.findById(id).toShowVehicleResponse()
    }

    @PostMapping("")
    fun createVehicle(vehicle: CreateVehicleRequest, principal: Principal): Vehicle {
        val newVehicle = vehicleService.create(
            Vehicle(
                -1,
                vehicle.name,
                creator=User.empty,
                coordinates=Coordinates.fromDTO(vehicle.coordinates),
                enginePower=vehicle.enginePower,
                vehicleType = vehicle.vehicleType,
                fuelType = vehicle.fuelType,
                creationDate = LocalDate.now()
            ),
            principal
        )
        return newVehicle
    }
}