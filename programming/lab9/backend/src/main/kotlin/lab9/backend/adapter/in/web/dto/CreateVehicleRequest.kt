package lab9.backend.adapter.`in`.web.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import lab9.backend.domain.Vehicle

@Serializable
data class CreateVehicleRequest(
        @NotBlank
        val name: String,
        @Min(-523)
        val x: Int,
        val y: Long?,
        @Min(0)
        val enginePower: Double,
        val vehicleType: Vehicle.VehicleType,
        val fuelType: Vehicle.FuelType?
)