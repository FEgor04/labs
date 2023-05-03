package lab9.backend.application.port.`in`.vehicles

import lab9.backend.domain.Vehicle

data class GetVehiclesResponse(
    val vehicles: List<Vehicle>,
    val totalElements: Int,
    val totalPages: Int,
)