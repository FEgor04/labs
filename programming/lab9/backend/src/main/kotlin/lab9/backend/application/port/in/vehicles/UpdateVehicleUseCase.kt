package lab9.backend.application.port.`in`.vehicles

import lab9.backend.application.port.`in`.user.PermissionDeniedException
import lab9.backend.common.UseCase
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle
import kotlin.jvm.Throws

@UseCase
interface UpdateVehicleUseCase {
    /**
     * Обновляет транспорт
     * @throws PermissionDeniedException если у пользователя, пытающегося обновить транспорт недостаточно прав
     */
    @Throws(PermissionDeniedException::class)
    fun updateVehicle(query: UpdateVehicleQuery): Vehicle
}

data class UpdateVehicleQuery(
    val vehicleID: Vehicle.VehicleID,
    val name: String,
    val coordinates: Vehicle.Coordinates,
    val enginePower: Double,
    val vehicleType: Vehicle.VehicleType,
    val fuelType: Vehicle.FuelType?,
    val actorID: User.UserID,
)