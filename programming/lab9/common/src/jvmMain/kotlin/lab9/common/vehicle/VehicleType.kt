package lab9.common.vehicle

import kotlinx.serialization.Serializable

@Serializable
actual enum class VehicleType {
    PLANE, SUBMARINE, BOAT, BICYCLE
}