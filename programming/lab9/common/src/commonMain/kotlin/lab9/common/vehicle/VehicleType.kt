@file:OptIn(ExperimentalJsExport::class)

package lab9.common.vehicle

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@Serializable
expect enum class VehicleType {
    PLANE,
    SUBMARINE,
    BOAT,
    BICYCLE;

    fun toJson(): String
}
