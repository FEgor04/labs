package lab6.server.domain.repositories

import lab6.shared.entities.CollectionInfo
import lab6.shared.entities.dtos.responses.ReplaceIfLowerResults
import lab6.shared.entities.vehicle.Vehicle
import lab6.shared.entities.vehicle.VehicleType

interface VehicleRepository {
    /**
     * Добавляет новое т/с в коллекцию.
     * Возвращает ID нового транспорта
     */
    fun add(veh: Vehicle): Int

    /**
     * Возвращает список автомобилей в коллекции
     */
    fun list(): List<Vehicle>

    /**
     * Считает количество элементов заданного типа в коллекции
     */
    fun countByType(type: VehicleType?): Int

    /**
     * Очищает коллекцию
     */
    fun clear()

    /**
     * Считает количетсво элементов коллекции, чья enginePower меньше, чем заданная
     */
    fun countLessThanEnginePower(power: Double): Int

    /**
     * Возвращает информацию о коллекции
     */
    fun getCollectionInfo(): CollectionInfo

    /**
     * Возвращает объект с наименьшим ID
     */
    fun getMinById(): Vehicle?

    /**
     * Удаляет все объекты коллекции, большие чем данный
     * Возвращает количество удаленных элементов.
     */
    fun removeGreater(vehicle: Vehicle): Int

    /**
     * Удаляет элемент коллекции
     */
    fun remove(id: Int)

    /**
     * Удаляет все элементы коллекции, меньшие чем данный.
     * Возвращает количество удаленных элементов.
     */
    fun removeLower(vehicle: Vehicle): Int

    /**
     * Заменяет элемент коллекции с данным ID, если он больше, чем данный.
     */
    fun replaceIfLower(id: Int, veh: Vehicle): ReplaceIfLowerResults
    fun updateVehicleById(newVehicle: Vehicle)

    /**
     * Сохраняет элементы коллекции в удаленное хранилище
     */
    fun save()

    /**
     * Загружает в коллекцию элементы из удаленного хранилища
     */
    fun load()
}