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
) {
    init {
        require(pageSize > 0) { "page size must be positive" }
        require(pageSize <= 50)  {"page size must be less or equal to 50"}
        require(pageNumber >= 0)  {"page size must be positive or zero"}
    }
}