package lab9.backend.application.port.`in`.vehicles

import lab9.backend.domain.User
import lab9.backend.domain.Vehicle

data class CreateVehicleQuery(
        val name: String,
        val creatorID: User.UserID,
        val coordinates: Vehicle.Coordinates,
        val enginePower: Double,
        val vehicleType: Vehicle.VehicleType,
        val fuelType: Vehicle.FuelType?
)