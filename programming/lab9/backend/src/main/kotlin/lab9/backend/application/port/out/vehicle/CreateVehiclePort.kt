package lab9.backend.application.port.out.vehicle

import lab9.backend.domain.Vehicle

interface CreateVehiclePort {
    fun createVehicle(vehicle: Vehicle): Vehicle
}