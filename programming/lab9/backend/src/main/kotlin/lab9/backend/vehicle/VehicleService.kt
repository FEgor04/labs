package lab9.backend.vehicle

import lab9.backend.entities.Vehicle
import lab9.common.requests.VehicleFilter
import lab9.common.requests.VehicleSorting
import org.springframework.data.domain.Page
import java.security.Principal

interface VehicleService {
    fun findAllWithPage(pageSize: Int, pageNumber: Int, filter: VehicleFilter?, sorting: VehicleSorting): Page<Vehicle>
    fun findById(id: Int): Vehicle
    fun create(vehicle: Vehicle, author: Principal): Vehicle
}