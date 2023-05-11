package lab9.backend.application.service

import lab9.backend.application.port.`in`.authorities.CheckAuthoritiesUseCase
import lab9.backend.application.port.`in`.user.PermissionDeniedException
import lab9.backend.application.port.`in`.vehicles.DeleteVehicleUseCase
import lab9.backend.application.port.out.events.SendEventPort
import lab9.backend.application.port.out.vehicle.DeleteVehiclePort
import lab9.backend.application.port.out.vehicle.GetVehiclesPort
import lab9.backend.domain.Event
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle
import org.springframework.stereotype.Service

@Service
class DeleteVehicleService(
    val deleteVehiclePort: DeleteVehiclePort,
    val getVehiclesPort: GetVehiclesPort,
    val checkAuthoritiesUseCase: CheckAuthoritiesUseCase,
    val sendEventPort: SendEventPort,
): DeleteVehicleUseCase {
    override fun deleteVehicle(vehicleID: Vehicle.VehicleID, actorId: User.UserID) {
        if(!checkAuthoritiesUseCase.checkUserCanDeleteVehicle(userID = actorId, vehicleID)) {
            throw PermissionDeniedException()
        }
        deleteVehiclePort.delete(vehicleID)
        sendEventPort.send(Event.VehicleDeleted(vehicleID, actorId))
    }
}