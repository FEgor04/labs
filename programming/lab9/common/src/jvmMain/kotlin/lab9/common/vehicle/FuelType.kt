package lab9.common.vehicle

import kotlinx.serialization.Serializable

@Serializable
actual enum class FuelType {
    GASOLINE, ELECTRICITY, MANPOWER, PLASMA, ANTIMATTER;

    actual fun toJson(): String {
        TODO("Not yet implemented")
    }
}