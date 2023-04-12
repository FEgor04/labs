package lab8.client.domain

import lab8.entities.dtos.responses.*
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType

/**
 * Интерфейс обработчика команд
 */
@Suppress("TooManyFunctions")
interface RemoteCommandHandler {
    val user: User

    /**
     * Добавляет в коллекцию новый транспорт. Возвращает его ID
     */
    suspend fun add(vehicle: Vehicle): Int
    suspend fun show(): List<Vehicle>
    suspend fun clear()
    suspend fun countByType(type: VehicleType?): Int
    suspend fun countLessThanEnginePower(power: Double): Int
    suspend fun getCollectionInfo(): InfoResponseDTO
    suspend fun getMinById(): Vehicle?
    suspend fun removeGreater(vehicle: Vehicle): Int
    suspend fun remove(id: Int)
    suspend fun removeLower(veh: Vehicle): Int
    suspend fun replaceIfLower(id: Int, element: Vehicle): ReplaceIfLowerResults
    suspend fun updateVehicleById(newVehicle: Vehicle)
    fun exit()
    suspend fun await()
    suspend fun tryToLogin(user: User): User
    suspend fun createUser(user: User): User
    suspend fun logout()
}
