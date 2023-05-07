package lab9.backend.adapter.`in`.web.dto

import kotlinx.serialization.Serializable
import lab9.backend.domain.Vehicle

@Serializable
data class ShowVehicleResponse(
        val id: Int,
        val name: String,
        val coordinates: CoordinatesDTO,
        val creationDate: String,
        val vehicleType: Vehicle.VehicleType,
        val fuelType: Vehicle.FuelType?,
        val creatorId: Int,
        val enginePower: Double,
        val canEdit: Boolean,
        val canDelete: Boolean
)
