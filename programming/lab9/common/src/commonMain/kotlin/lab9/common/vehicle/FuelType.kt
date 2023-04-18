package lab9.common.vehicle

import kotlinx.serialization.Serializable

expect enum class FuelType {
    GASOLINE,
    ELECTRICITY,
    MANPOWER,
    PLASMA,
    ANTIMATTER
}