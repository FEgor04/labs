package lab9.backend.application.port.`in`.vehicles

import lab9.backend.domain.User
import lab9.backend.domain.Vehicle
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType

data class CreateVehicleQuery(
    val name: String,
    val creatorID: User.UserID,
    val coordinates: Vehicle.Coordinates,
    val enginePower: Double,
    val vehicleType: VehicleType,
    val fuelType: FuelType?
)