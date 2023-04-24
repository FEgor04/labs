package lab9.backend.vehicle

import lab9.backend.entities.Vehicle
import lab9.common.requests.VehicleFilter
import lab9.common.requests.VehicleSorting
import org.springframework.data.domain.Page
import java.security.Principal
import lab9.backend.exceptions.ResourceNotFoundException

interface VehicleService {
    fun findAllWithPage(pageSize: Int, pageNumber: Int, filter: VehicleFilter?, sorting: VehicleSorting): Page<Vehicle>

    /**
     * Находит транспорт по заданному ID
     * @throws ResourceNotFoundException если транспорта с таким ID не существует
     */
    fun findById(id: Int): Vehicle

    fun create(vehicle: Vehicle, author: Principal): Vehicle
}