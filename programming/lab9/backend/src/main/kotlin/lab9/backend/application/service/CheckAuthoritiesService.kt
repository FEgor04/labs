package lab9.backend.application.service

import lab9.backend.application.port.`in`.authorities.GetUserAuthoritiesUseCase
import lab9.backend.application.port.`in`.vehicles.VehicleNotFoundException
import lab9.backend.application.port.out.vehicle.GetVehiclesPort
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle
import org.springframework.stereotype.Service

@Service
class CheckAuthoritiesService(
    private val userAuthoritiesUseCase: GetUserAuthoritiesUseCase,
    private val getVehiclesPort: GetVehiclesPort,
) : lab9.backend.application.port.`in`.authorities.CheckAuthoritiesUseCase {
    override fun checkUserCanDeleteVehicle(userID: User.UserID, vehicleID: Vehicle.VehicleID): Boolean {
        val vehicleCreator = (getVehiclesPort.getVehicle(vehicleID) ?: throw VehicleNotFoundException()).creatorID
        if (vehicleCreator == userID) {
            return true
        }
        val userAuthorities = userAuthoritiesUseCase.getUserAuthoritiesToDelete(userID)
        return vehicleCreator in userAuthorities
    }

    override fun checkUserCanEditVehicle(userID: User.UserID, vehicleID: Vehicle.VehicleID): Boolean {
        val vehicleCreator = (getVehiclesPort.getVehicle(vehicleID) ?: throw VehicleNotFoundException()).creatorID
        if (vehicleCreator == userID) {
            return true
        }
        val userAuthorities = userAuthoritiesUseCase.getUserAuthoritiesToEdit(userID)
        return vehicleCreator in userAuthorities
    }

}