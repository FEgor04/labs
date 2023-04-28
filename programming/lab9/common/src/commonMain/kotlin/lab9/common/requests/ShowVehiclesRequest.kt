@file:OptIn(ExperimentalJsExport::class)

package lab9.common.requests

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class ShowVehiclesRequest(
    val pageSize: Int = 10,
    val pageNumber: Int = 0,
    val filter: VehicleFilter?,
    val sorting: VehicleSorting?,
)