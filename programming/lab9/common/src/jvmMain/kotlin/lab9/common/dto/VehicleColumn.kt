package lab9.common.dto

import kotlinx.serialization.Serializable

@Serializable
actual enum class VehicleColumn {
    ID, NAME, COORDINATES_X, COORDINATES_Y, CREATION_DATE, ENGINE_POWER, FUEL_TYPE, VEHICLE_TYPE, CREATOR_ID,
}