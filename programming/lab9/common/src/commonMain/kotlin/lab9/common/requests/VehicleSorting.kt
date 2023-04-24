@file:OptIn(ExperimentalJsExport::class)

package lab9.common.requests

import kotlinx.serialization.Serializable
import lab9.common.dto.VehicleColumn
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class VehicleSorting(
    val column: VehicleColumn,
    val asc: Boolean,
)