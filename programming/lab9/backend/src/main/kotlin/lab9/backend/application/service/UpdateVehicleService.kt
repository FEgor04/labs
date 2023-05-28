package lab9.backend.application.service

import lab9.backend.application.port.`in`.authorities.CheckAuthoritiesUseCase
import lab9.backend.application.port.`in`.authorities.GetUserAuthoritiesUseCase
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.user.PermissionDeniedException
import lab9.backend.application.port.`in`.vehicles.UpdateVehicleQuery
import lab9.backend.application.port.`in`.vehicles.UpdateVehicleUseCase
import lab9.backend.application.port.out.events.SendEventPort
import lab9.backend.application.port.out.vehicle.UpdateVehiclePort
import lab9.backend.domain.Event
import lab9.backend.domain.Vehicle
import org.springframework.stereotype.Service


@Service
class UpdateVehicleService(
    private val updateVehiclePort: UpdateVehiclePort,
    private val checkAuthoritiesUseCase: CheckAuthoritiesUseCase,
    private val sendEventPort: SendEventPort,
) : UpdateVehicleUseCase {
    override fun updateVehicle(query: UpdateVehicleQuery): Vehicle {
        if (!checkAuthoritiesUseCase.checkUserCanEditVehicle(query.actorID, query.vehicleID)) {
            throw PermissionDeniedException()
        }
        val updatedVehicle = updateVehiclePort.updateVehicle(query)
        sendEventPort.send(Event.VehicleUpdated(query.vehicleID, query.actorID))
        return updatedVehicle
    }
}