package lab9.backend.application.port.`in`.vehicles

import lab9.backend.common.UseCase
import lab9.backend.domain.Vehicle

@UseCase
interface GetVehiclesUseCase {
    fun getVehicles(query: GetVehiclesQuery): PagedResponse<Vehicle>
    fun getVehicleById(id: Vehicle.VehicleID): Vehicle?
}