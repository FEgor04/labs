@file:OptIn(ExperimentalJsExport::class)

package lab9.common.responses

import kotlinx.serialization.Serializable
import lab9.common.dto.VehicleDTO
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@JsExport
@Serializable
data class ShowUserResponse(
    val id: Int,
    val username: String,
)