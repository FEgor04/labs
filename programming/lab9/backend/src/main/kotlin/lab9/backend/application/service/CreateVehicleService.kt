package lab9.backend.application.service

import lab9.backend.application.port.`in`.vehicles.CreateVehicleQuery
import lab9.backend.application.port.`in`.vehicles.CreateVehicleUseCase
import lab9.backend.application.port.out.vehicle.CreateVehiclePort
import lab9.backend.application.port.out.notification.SendEventPort
import lab9.backend.domain.Event
import lab9.backend.domain.Vehicle
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CreateVehicleService(
    private val createVehiclePort: CreateVehiclePort,
    private val sendEventPort: SendEventPort,
) : CreateVehicleUseCase {
    override fun create(query: CreateVehicleQuery): Vehicle {
        val newVehicle = createVehiclePort.createVehicle(
            Vehicle.withoutID(
                query.name,
                query.creatorID,
                coordinates = query.coordinates,
                creationDate = LocalDate.now(),
                query.enginePower,
                query.vehicleType,
                query.fuelType,
            )
        )
        sendEventPort.send(Event.NewVehicle(
            newVehicle.id,
        ))
        return newVehicle
    }
}