package lab8.server.domain.usecases

import lab8.entities.CollectionInfo
import lab8.entities.dtos.responses.ReplaceIfLowerResults
import lab8.entities.events.CollectionEvent
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType
import lab8.logger.KCoolLogger
import lab8.server.domain.auth.AuthManager
import lab8.server.domain.notifier.Notifier
import lab8.server.domain.repositories.VehicleRepository

@Suppress("TooManyFunctions")
class CommandsHandlerUseCase(
    private val repository: VehicleRepository,
    private val manager: AuthManager,
    private val notifier: Notifier,
) {
    private val logger by KCoolLogger()
    val collectionInfo: CollectionInfo
        get() {
            return repository.getCollectionInfo()
        }

    suspend fun add(vehicle: Vehicle, user: User): Int {
        val resVehicle = vehicle.copy(id = repository.add(vehicle, user), authorID = user.id)
        notifier.notify(CollectionEvent.ForceUpdate())
        return resVehicle.id
    }

    fun list(): List<Vehicle> {
        return repository.list()
    }

    fun countByType(type: VehicleType?): Int {
        return repository.countByType(type)
    }

    suspend fun clear(user: User) {
        repository.clear(user)
        notifier.notify(CollectionEvent.ForceUpdate())
    }

    fun countLessThanEnginePower(power: Double): Int {
        return repository.countLessThanEnginePower(power)
    }

    fun getMinById(): Vehicle? {
        return repository.getMinById()
    }

    suspend fun removeGreater(veh: Vehicle, user: User): Int {
        val res = repository.removeGreater(veh, user)
        notifier.notify(CollectionEvent.ForceUpdate())
        return res
    }

    suspend fun removeLower(veh: Vehicle, user: User): Int {
        val res = repository.removeLower(veh, user)
        notifier.notify(CollectionEvent.ForceUpdate())
        return res
    }

    suspend fun remove(id: Int, user: User) {
        repository.remove(id, user)
        notifier.notify(CollectionEvent.ForceUpdate())
    }

    suspend fun replaceIfLower(id: Int, veh: Vehicle, user: User): ReplaceIfLowerResults {
        val res = repository.replaceIfLower(id, veh, user)
        notifier.notify(CollectionEvent.ForceUpdate())
        return res
    }

    suspend fun update(id: Int, veh: Vehicle, user: User) {
        logger.info("Updating vehicle #${id} by ${user.id}")
        repository.updateVehicleById(veh.copy(id = id, authorID = user.id), user)
        notifier.notify(CollectionEvent.ForceUpdate())
    }

    /**
     * Создает нового пользователя.
     * Возвращает ID созданного пользователя.
     */
    suspend fun createUser(login: String, password: String) = manager.createUser(login, password)

    /**
     * Проверяет, существует ли пользователь с данным логином и паролем в базе данных.
     * @return ID пользователя, если такой пользователь существует и пароль верный
     * и 0, если пользователя не существует или был дан неверный пароль
     */
    suspend fun authUser(user: User): Int = manager.authUser(user.name, user.password)
}
