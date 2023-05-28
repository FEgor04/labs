package lab9.backend.application.port.out.vehicle

import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.application.port.`in`.vehicles.PagedResponse
import lab9.backend.domain.Vehicle

interface GetVehiclesPort {
    fun getVehicles(query: GetVehiclesQuery): PagedResponse<Vehicle>
    fun getVehicle(id: Vehicle.VehicleID): Vehicle?
}