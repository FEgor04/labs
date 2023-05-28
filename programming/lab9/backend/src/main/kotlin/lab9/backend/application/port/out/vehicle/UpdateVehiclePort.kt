package lab9.backend.application.port.out.vehicle

import lab9.backend.application.port.`in`.vehicles.UpdateVehicleQuery
import lab9.backend.application.port.`in`.vehicles.VehicleNotFoundException
import lab9.backend.domain.Vehicle

interface UpdateVehiclePort {
    /**
     * Обновляет транспорт
     * @throws VehicleNotFoundException если транспорт с таким ID не существует
     */
    @Throws(VehicleNotFoundException::class)
    fun updateVehicle(query: UpdateVehicleQuery): Vehicle
}