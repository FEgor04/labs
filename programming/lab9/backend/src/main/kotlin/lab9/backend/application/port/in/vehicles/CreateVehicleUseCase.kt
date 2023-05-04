package lab9.backend.application.port.`in`.vehicles

import lab9.backend.common.UseCase
import lab9.backend.domain.User
import lab9.backend.domain.Vehicle

@UseCase
interface CreateVehicleUseCase {
    /**
     * Создает новый транспорт
     * @throws VehicleAlreadyExistsException если транспорт с таким названием уже существует
     */
    fun create(query: CreateVehicleQuery): Vehicle
}