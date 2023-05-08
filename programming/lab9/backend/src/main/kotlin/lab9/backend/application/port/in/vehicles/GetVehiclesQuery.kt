package lab9.backend.application.port.`in`.vehicles

import lab9.backend.domain.Vehicle

data class GetVehiclesQuery(
    val pageNumber: Int,
    val pageSize: Int,
    val sorting: Sorting,
) {
    data class Sorting(
        val field: Vehicle.Field,
        val asc: Boolean,
    )
}