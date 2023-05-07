package lab9.backend.application.port.`in`.authorities

import lab9.backend.application.port.`in`.vehicles.VehicleNotFoundException
import lab9.backend.common.UseCase
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle

@UseCase
interface CheckAuthoritiesUseCase {
    /**
     * Проверяет, может ли пользователь удалить транспорт
     * @throws VehicleNotFoundException если транспорта с таким ID не существует
     * @return true, если может, false -- иначе
     */
    @Throws(VehicleNotFoundException::class)
    fun checkUserCanDeleteVehicle(userID: User.UserID, vehicleID: Vehicle.VehicleID): Boolean

    /**
     * Проверяет, может ли пользователь редактировать транспорт
     * @return true, если может, false -- иначе
     * @throws VehicleNotFoundException если транспорта с таким ID не существует
     */
    @Throws(VehicleNotFoundException::class)
    fun checkUserCanEditVehicle(userID: User.UserID, vehicleID: Vehicle.VehicleID): Boolean
}