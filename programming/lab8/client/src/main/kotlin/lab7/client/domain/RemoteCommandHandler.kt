package lab7.client.domain

import lab7.entities.dtos.responses.*
import lab7.entities.vehicle.Vehicle
import lab7.entities.vehicle.VehicleType

/**
 * Интерфейс обработчика команд
 */
@Suppress("TooManyFunctions")
interface RemoteCommandHandler {
    /**
     * Добавляет в коллекцию новый транспорт. Возвращает его ID
     */
    fun add(vehicle: Vehicle): Int
    fun show(): List<Vehicle>
    fun clear()
    fun countByType(type: VehicleType?): Int
    fun countLessThanEnginePower(power: Double): Int
    fun getCollectionInfo(): InfoResponseDTO
    fun getMinById(): Vehicle?
    fun removeGreater(vehicle: Vehicle): Int
    fun remove(id: Int)
    fun removeLower(veh: Vehicle): Int
    fun replaceIfLower(id: Int, element: Vehicle): ReplaceIfLowerResults
    fun updateVehicleById(newVehicle: Vehicle)
    fun exit()
    fun await()
}
