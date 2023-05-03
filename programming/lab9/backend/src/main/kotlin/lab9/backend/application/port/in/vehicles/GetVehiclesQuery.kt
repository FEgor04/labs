package lab9.backend.application.port.`in`.vehicles

data class GetVehiclesQuery(
    val pageNumber: Int,
    val pageSize: Int
)