package lab9.backend.application.service

import lab9.backend.application.port.`in`.vehicles.DeleteVehicleUseCase
import lab9.backend.application.port.`in`.vehicles.VehicleNotFoundException
import lab9.backend.application.port.out.vehicle.DeleteVehiclePort
import lab9.backend.application.port.out.vehicle.GetVehiclesPort
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle
import org.springframework.stereotype.Service

@Service
class DeleteVehicleService(
    val deleteVehiclePort: DeleteVehiclePort,
    val getVehiclesPort: GetVehiclesPort
): DeleteVehicleUseCase {
    override fun deleteVehicle(vehicleID: Vehicle.VehicleID, actorId: User.UserID) {
        val vehicle = getVehiclesPort.getVehicle(vehicleID) ?: throw VehicleNotFoundException()
    }
}