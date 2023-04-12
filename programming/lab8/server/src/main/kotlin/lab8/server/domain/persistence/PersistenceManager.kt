package lab8.server.domain.persistence

import kotlinx.coroutines.flow.Flow
import lab8.entities.dtos.responses.ReplaceIfLowerResults
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle
import lab8.exceptions.ServerException

interface PersistenceManager {
    /**
     * Сохраняет vehicle в базу данных
     */
    suspend fun saveVehicle(veh: Vehicle, authorLogin: String): Int

    /**
     * Загружает все автомобили из базы данных.
     */
    fun loadAllVehicles(): Flow<Vehicle>

    /**
     * Очищает коллекцию и базу данных
     */
    suspend fun clear(user: User)

    /**
     * Удаляет все машины, принадлежащие данному пользователю, которые больше данной
     * @return количество удаленных машин
     */
    suspend fun removeGreater(vehicle: Vehicle, author: User): Int

    /**
     * Удаляет все машины, принадлежащие данному пользователю, которые меньше данной
     * @return количество удаленных машин
     */
    suspend fun removeLower(veh: Vehicle, author: User): Int

    /**
     * Удаляет машину с заданным ID, если она принадлежит автору
     * @throws ServerException.BadOwnerException если машина не принадлежит автору
     */
    suspend fun remove(id: Int, author: User)

    /**
     * Заменяет машину с заданным ID на новую, если новая меньше, чем данная.
     */
    suspend fun replaceIfLower(id: Int, veh: Vehicle, author: User): ReplaceIfLowerResults

    /**
     * Заменяет старую машину на новую
     * @throws ServerException.BadOwnerException если машина не принадлежит автору
     */
    suspend fun update(newVehicle: Vehicle, user: User): Int
}
