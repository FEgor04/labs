package lab9.backend.adapter.`in`.web.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShowVehiclesResponse(
        val totalPages: Int,
        val totalElements: Int,
        val vehicles: List<ShowVehicleResponse>
)
