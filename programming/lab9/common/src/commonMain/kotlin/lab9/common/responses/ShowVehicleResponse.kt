@file:OptIn(ExperimentalJsExport::class)

package lab9.common.responses

import kotlinx.serialization.Serializable
import lab9.common.dto.CoordinatesDTO
import lab9.common.dto.UserDTO
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class ShowVehicleResponse(
    val id: Int,
    val name: String,
    val coordinates: CoordinatesDTO,
    val creationDate: String,
    val enginePower: Double,
    val vehicleType: VehicleType,
    val fuelType: FuelType?,
    val creatorId: Int,
    val canEdit: Boolean = false,
    val canDelete: Boolean = false,
)