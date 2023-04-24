@file:OptIn(ExperimentalJsExport::class)
package lab9.common.responses

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class ShowVehiclesResponse(
    val vehicles: Array<ShowVehicleResponse>,
    val totalPages: Int,
    val totalElements: Int,
)
