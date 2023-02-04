package lab5.repositories

import lab5.entities.ValidationException
import lab5.entities.vehicle.Vehicle
import lab5.entities.vehicle.VehicleType
import kotlin.jvm.Throws

/**
 * Интерфейс репозитория транспорта
 */
interface VehicleRepository: Iterable<Vehicle> {
    /**
     * Добавляет новый транспорт
     * @throws ValidationException если vehicle не валидно
     */
    @Throws(ValidationException::class)
    fun insertVehicle(vehicle: Vehicle)

    /**
     * Получает транспорт по ID
     */
    fun getVehicleById(id: Int): Vehicle?

    /**
     * Обновляет транспорт по его ID
     */
    fun updateVehicleById(vehicle: Vehicle)

    /**
     * Возвращает список всех транспортных средств
     */
    fun listAllVehicles(): Iterable<Vehicle>

    /**
     * Удаляет транспорт из базы данных
     */
    fun removeVehicle(id: Int)

    /**
     * Сохраняет коллекцию в файл / удаленную БД
     */
    fun saveCollection()

    /**
     * Возвращает информацию о коллекции
     */
    fun getCollectionInfo(): CollectionInfo

    /**
     * Загружает данные в коллекцию из удаленного источника (например файла)
     */
    fun loadData()

    /**
     * Удаялет из колекции все элементы, большие чем данный
     */
    fun removeGreater(veh: Vehicle)

    /**
     * Удаялет из колекции все элементы, меньшие чем данный
     */
    fun removeLower(veh: Vehicle)

    /**
     * Заменят элемент по ключу ID если veh меньше чем элемент с ключом ID
     */
    fun replaceIfLower(id: Int, vehicle: Vehicle): ReplaceIfLowerResults

    /**
     * Возвращает минимальный элемент в коллекции.
     * Если в коллекции нет элементов, возвращает null
     */
    fun getMinById(): Vehicle?


    /**
     * Возвращает количество элементов в коллекции, чем тип равен данному
     */
    fun countByType(type: VehicleType?): Int

    /**
     * Возвращает количество элементов в коллекции, чья мощность двигателя меньше, чем данная
     */
    fun countLessThanEnginePower(enginePower: Double): Int

    /**
     * Очищает коллекцию
     */
    fun clear()

}