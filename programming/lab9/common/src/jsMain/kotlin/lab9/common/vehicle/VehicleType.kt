@file:OptIn(ExperimentalJsExport::class)

package lab9.common.vehicle

import kotlinx.serialization.Serializable

@JsExport
@Serializable
actual enum class VehicleType {
    PLANE, SUBMARINE, BOAT, BICYCLE;

    actual fun toJson(): String {
        return this.name
    }
}