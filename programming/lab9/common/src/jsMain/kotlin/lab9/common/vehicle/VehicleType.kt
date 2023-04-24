@file:OptIn(ExperimentalJsExport::class)

package lab9.common.vehicle

import kotlinx.serialization.Serializable

@JsExport
@Serializable
actual enum class VehicleType {
    PLANE, SUBMARINE, BOAT, BICYCLE
}