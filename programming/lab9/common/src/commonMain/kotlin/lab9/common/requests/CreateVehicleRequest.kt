package lab9.common.requests

import kotlinx.serialization.Serializable
import lab9.common.dto.CoordinatesDTO
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType

@Serializable
data class CreateVehicleRequest(
    val name: String,
    val coordinates: CoordinatesDTO,
    val enginePower: Double,
    val vehicleType: VehicleType,
    val fuelType: FuelType? = null,
) {
    init {
        require(name.isNotBlank()) { "name should not be blank" }
        require(enginePower > 0) { "engine power should be > 0" }
    }
}