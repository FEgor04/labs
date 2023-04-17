package lab9.backend.vehicle

import lab9.backend.entities.Vehicle
import java.security.Principal

interface VehicleService {
    fun findAllWithPage(pageSize: Int, pageNumber: Int): List<Vehicle>
    fun findById(id: Int): Vehicle
    fun create(vehicle: Vehicle, author: Principal): Vehicle
}