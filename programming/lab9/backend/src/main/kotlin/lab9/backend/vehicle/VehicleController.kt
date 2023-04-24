package lab9.backend.vehicle

import lab9.backend.entities.Coordinates
import lab9.backend.entities.User
import lab9.backend.entities.Vehicle
import lab9.backend.logger.KCoolLogger
import lab9.common.dto.VehicleColumn
import lab9.common.requests.CreateVehicleRequest
import lab9.common.requests.ShowVehiclesRequest
import lab9.common.requests.VehicleFilter
import lab9.common.requests.VehicleSorting
import lab9.common.responses.ShowVehicleResponse
import lab9.common.responses.ShowVehiclesResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
        @RequestParam
        pageSize: Int,
        @RequestParam
        pageNumber: Int,
        @RequestParam
        sortingColumn: Int,
        @RequestParam
        isAscending: Boolean,
        @RequestParam
        filter: VehicleFilter?
    ): ShowVehiclesResponse {
        val request = ShowVehiclesRequest(
            pageSize,
            pageNumber,
            filter,
            VehicleSorting(VehicleColumn.values()[sortingColumn], isAscending)
        )
        logger.info("Handling GET request at /api/vehicles. pageSize: ${request.pageSize}, pageNumber: ${request.pageNumber}, sorting: ${request.sorting}")
        val vehicles = vehicleService.findAllWithPage(
            request.pageSize,
            request.pageNumber,
            request.filter,
            request.sorting ?: VehicleSorting(VehicleColumn.ID, true)
        )
        return ShowVehiclesResponse(
            vehicles = vehicles.content.map { it.toShowVehicleResponse() }.toTypedArray(),
            totalPages = vehicles.totalPages,
            totalElements = vehicles.totalElements.toInt(),
        )
    }

    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable id: Int): ShowVehicleResponse {
        return vehicleService.findById(id).toShowVehicleResponse()
    }

    @PostMapping("")
    fun createVehicle(@RequestBody vehicle: CreateVehicleRequest, principal: Principal): Vehicle {
        val newVehicle = vehicleService.create(
            Vehicle(
                -1,
                vehicle.name,
                creator = User.empty,
                coordinates = Coordinates.fromDTO(vehicle.coordinates),
                enginePower = vehicle.enginePower,
                vehicleType = vehicle.vehicleType,
                fuelType = vehicle.fuelType,
                creationDate = LocalDate.now()
            ),
            principal
        )
        return newVehicle
    }
}