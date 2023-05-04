package lab9.backend.application.service

import lab9.backend.application.port.`in`.vehicles.CreateVehicleQuery
import lab9.backend.application.port.`in`.vehicles.CreateVehicleUseCase
import lab9.backend.application.port.out.vehicle.CreateVehiclePort
import lab9.backend.application.port.out.notification.SendNotificationPort
import lab9.backend.domain.Notification
import lab9.backend.domain.Vehicle
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CreateVehicleService(
    private val createVehiclePort: CreateVehiclePort,
    private val sendNotificationPort: SendNotificationPort,
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
        sendNotificationPort.notify(Notification.NewVehicleNotification(newVehicle.id))
        return newVehicle
    }
}