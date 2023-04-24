@file:OptIn(ExperimentalJsExport::class)
@file:JsExport()

package lab9.common.dto

import kotlinx.serialization.Serializable
import lab9.common.vehicle.FuelType
import lab9.common.vehicle.VehicleType
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class VehicleDTO(
    val id: Int,
    val name: String,
    val coordinates: CoordinatesDTO,
    val creationDate: String,
    val enginePower: Double,
    val vehicleType: VehicleType,
    val fuelType: FuelType?,
) {

    companion object {
        fun getColumnName(id: Int): String {
            return when (id) {
                0 -> "id"
                1 -> "name"
                2 -> "coordinatesX"
                3 -> "coordinatesY"
                4 -> "creationDate"
                5 -> "enginePower"
                6 -> "fuelType"
                7 -> "vehicleType"
                8 -> "creator"
                else -> "id"
            }
        }

        fun getColumns(): Array<String> {
            return (0..8).map { getColumnName(it) }.toTypedArray()
        }
    }
}
