package lab6.client.domain

import lab6.shared.entities.dtos.responses.*
import lab6.shared.entities.vehicle.Vehicle
import lab6.shared.entities.vehicle.VehicleType
import java.io.File

/**
 * Интерфейс обработчика команд
 */
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
}