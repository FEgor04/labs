package lab9.backend.application.port.out.vehicle

import lab9.backend.application.port.`in`.vehicles.VehicleNotFoundException
import lab9.backend.domain.Vehicle
import kotlin.jvm.Throws

interface DeleteVehiclePort {
    /**
     * Удаляет транспорт с заданным ID
     * @throws VehicleNotFoundException если такой транспорт не существует
     */
    @Throws(VehicleNotFoundException::class)
    fun delete(vehicleID: Vehicle.VehicleID)
}