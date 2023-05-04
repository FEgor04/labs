package lab9.backend.application.port.`in`.vehicles

import lab9.backend.application.port.`in`.user.PermissionDeniedException
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle
import kotlin.jvm.Throws

interface DeleteVehicleUseCase {
    /**
     * Удаляет транспорт с заданным ID.
     * @throws VehicleNotFoundException если такого транспорта не существует
     * @throws PermissionDeniedException если у пользователя недостаточно прав
     *
     */
    @Throws(VehicleNotFoundException::class, PermissionDeniedException::class)
    fun deleteVehicle(vehicleID: Vehicle.VehicleID, actorId: User.UserID)
}