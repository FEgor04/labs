package lab9.common.responses

import kotlinx.serialization.Serializable
import lab9.common.dto.VehicleDTO

@Serializable
data class ShowUserResponse(
    val id: Int,
    val username: String,
    val vehicles: List<VehicleDTO>
)