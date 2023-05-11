package lab9.backend.application.service

import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.application.port.`in`.vehicles.GetVehiclesResponse
import lab9.backend.application.port.`in`.vehicles.GetVehiclesUseCase
import lab9.backend.application.port.out.vehicle.GetVehiclesPort
import lab9.backend.domain.Vehicle
import org.springframework.stereotype.Service

@Service
class GetVehiclesService(
    private val getVehiclesPort: GetVehiclesPort
): GetVehiclesUseCase {
    override fun getVehicles(query: GetVehiclesQuery): GetVehiclesResponse {
        return getVehiclesPort.getVehicles(query)
    }

    override fun getVehicleById(id: Vehicle.VehicleID): Vehicle? {
        return getVehiclesPort.getVehicle(id)
    }
}