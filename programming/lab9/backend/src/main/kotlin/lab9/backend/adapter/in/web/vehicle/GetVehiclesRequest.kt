package lab9.backend.adapter.`in`.web.vehicle

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.validation.annotation.Validated

data class GetVehiclesRequest(
    @Min(0)
    val pageNumber: Int = 0,
    @Min(0)
    @Max(100)
    val pageSize: Int = 10,
)
