package lab9.backend.application.port.`in`.vehicles

import lab9.backend.common.UseCase
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle

@UseCase
interface UpdateVehicleUseCase {
    fun updateVehicle(query: UpdateVehicleQuery): Vehicle
}

data class UpdateVehicleQuery(
    val vehicleID: Vehicle.VehicleID,
    val name: String,
    val coordinates: Vehicle.Coordinates,
    val enginePower: Double,
    val vehicleType: Vehicle.VehicleType,
    val fuelType: Vehicle.FuelType?,
    val actorID: User.UserID,
)