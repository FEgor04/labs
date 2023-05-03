package lab9.backend.application.port.`in`.vehicles

import lab9.backend.common.UseCase

@UseCase
interface GetVehiclesUseCase {
    fun getVehicles(query: GetVehiclesQuery): GetVehiclesResponse
}