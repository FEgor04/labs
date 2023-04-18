package lab9.common.dto

import kotlinx.serialization.Serializable
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType

@Serializable
data class VehicleDTO(
    val id: Int,
    val name: String,
    val coordinates: CoordinatesDTO,
    val creationDate: String,
    val enginePower: Double,
    val vehicleType: VehicleType,
    val fuelType: FuelType?,
)
