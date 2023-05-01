@file:OptIn(ExperimentalJsExport::class)
@file:JsExport()

package lab9.common.requests

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab9.common.dto.CoordinatesDTO
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@Serializable
data class CreateVehicleRequest(
    val name: String,
    val coordinates: CoordinatesDTO,
    val enginePower: Double,
    val vehicleType: VehicleType,
    val fuelType: FuelType? = null
) {

}