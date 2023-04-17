package lab9.common.responses

import lab9.common.dto.VehicleDTO

data class ShowUserResponse(
    val id: Int,
    val username: String,
    val vehicles: List<VehicleDTO>
)