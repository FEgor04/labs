package lab8.server.domain.repositories

import lab8.entities.CollectionInfo
import lab8.entities.dtos.responses.ReplaceIfLowerResults
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType

@Suppress("TooManyFunctions")
interface VehicleRepository {
    /**
     * Добавляет новое т/с в коллекцию.
     * Возвращает ID нового транспорта
     */
    suspend fun add(veh: Vehicle, author: User): Int

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
    suspend fun clear(user: User)

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
    suspend fun removeGreater(vehicle: Vehicle, author: User): Int

    /**
     * Удаляет элемент коллекции
     * @throws lab8.exceptions.ServerException.BadOwnerException если машина не принадлежит автору
     */
    suspend fun remove(id: Int, author: User)

    /**
     * Удаляет все элементы коллекции, меньшие чем данный.
     * Возвращает количество удаленных элементов.
     */
    suspend fun removeLower(vehicle: Vehicle, author: User): Int

    /**
     * Заменяет элемент коллекции с данным ID, если он больше, чем данный.
     */
    suspend fun replaceIfLower(id: Int, veh: Vehicle, author: User): ReplaceIfLowerResults
    suspend fun updateVehicleById(newVehicle: Vehicle, author: User)

    /**
     * Загружает в коллекцию элементы из удаленного хранилища
     */
    fun load()
}
