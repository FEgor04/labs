package lab9.backend.vehicle.requests

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import lab9.common.dto.VehicleColumn
import lab9.common.requests.VehicleFilter
import lab9.common.requests.VehicleSorting

data class GetVehiclesRequest(
    @Min(1)
    @Max(50)
    val pageSize: Int = 10,
    @Min(0)
    val pageNumber: Int = 0,
    val sorting: VehicleSorting = VehicleSorting(VehicleColumn.ID, true),
    val filter: VehicleFilter? = null
)