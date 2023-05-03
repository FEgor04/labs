package lab9.backend.application.port.out

import lab9.backend.application.port.`in`.vehicles.GetVehiclesQuery
import lab9.backend.application.port.`in`.vehicles.GetVehiclesResponse

interface GetVehiclesPort {
    fun getVehicles(query: GetVehiclesQuery): GetVehiclesResponse
}