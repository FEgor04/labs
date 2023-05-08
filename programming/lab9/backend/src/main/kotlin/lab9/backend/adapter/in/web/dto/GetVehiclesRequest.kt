package lab9.backend.adapter.`in`.web.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import kotlinx.serialization.Serializable
import org.springframework.validation.annotation.Validated

@Serializable
data class GetVehiclesRequest(
    @Min(0)
    val pageNumber: Int = 0,
    @Min(0)
    @Max(100)
    val pageSize: Int = 10,
    val sorting: GetVehiclesSorting,
)
