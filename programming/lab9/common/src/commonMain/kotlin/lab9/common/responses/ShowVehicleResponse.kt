package lab9.common.responses

import lab9.common.dto.CoordinatesDTO
import lab9.common.dto.UserDTO
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType

data class ShowVehicleResponse(
    val id: Int,
    val name: String,
    val coordinates: CoordinatesDTO,
    val creationDate: String,
    val enginePower: Double,
    val vehicleType: VehicleType,
    val fuelType: FuelType?,
    val creator: UserDTO,
)