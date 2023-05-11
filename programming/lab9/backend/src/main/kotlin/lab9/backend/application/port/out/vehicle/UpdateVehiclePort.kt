package lab9.backend.application.port.out.vehicle

import lab9.backend.application.port.`in`.vehicles.UpdateVehicleQuery
import lab9.backend.domain.Vehicle

/**
 * @author fegor04
 */
interface UpdateVehiclePort {
    fun updateVehicle(query: UpdateVehicleQuery): Vehicle
}