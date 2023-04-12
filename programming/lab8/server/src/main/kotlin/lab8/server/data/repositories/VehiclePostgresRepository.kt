package lab8.server.data.repositories

import lab8.entities.CollectionInfo
import lab8.entities.dtos.responses.ReplaceIfLowerResults
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType
import lab8.exceptions.ServerException
import lab8.logger.KCoolLogger
import lab8.server.domain.persistence.PersistenceManager
import lab8.server.domain.repositories.VehicleRepository
import java.time.LocalDate
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import java.util.stream.Collectors
import kotlin.time.ExperimentalTime

@Suppress("TooManyFunctions")
class VehiclePostgresRepository(
    private val persistenceManager: PersistenceManager,
) : VehicleRepository {
    private val logger by KCoolLogger()

    override suspend fun add(veh: Vehicle, author: User): Int {
        val newId = persistenceManager.saveVehicle(veh, author.name)
        logger.info("Added new vehicle to database. Adding it to map")
        val newVehicle = veh.copy(
            id = newId,
            creationDate = LocalDate.now()
        )
        this.load()
        return newId
    }

    override fun list(): List<Vehicle> {
        TODO("WTF?")
    }

    override fun countByType(type: VehicleType?): Int {
        TODO("WTF?")
    }

    override suspend fun clear(user: User) {
        TODO("WTF?")
    }

    override fun countLessThanEnginePower(power: Double): Int {
        TODO("WTF?")
    }

    override fun getCollectionInfo(): CollectionInfo {
        TODO("WTF?")
    }

    override fun getMinById(): Vehicle? {
        TODO("WTF?")
    }

    override suspend fun removeGreater(vehicle: Vehicle, author: User): Int {
        return persistenceManager.removeGreater(vehicle, author)
    }

    override suspend fun remove(id: Int, author: User) {
        persistenceManager.remove(id, author)
    }

    override suspend fun removeLower(vehicle: Vehicle, author: User): Int {
        return persistenceManager.removeLower(vehicle, author)
    }

    @Suppress("ReturnCount")
    override suspend fun replaceIfLower(id: Int, veh: Vehicle, author: User): ReplaceIfLowerResults {
        return persistenceManager.replaceIfLower(id, veh, author)
    }

    @Suppress("TooGenericExceptionThrown")
    override suspend fun updateVehicleById(newVehicle: Vehicle, author: User) {
        persistenceManager.update(newVehicle, author)
    }

    @OptIn(ExperimentalTime::class)
    override fun load() {
//        val newMap = TreeMap<Int, Vehicle>()
//        val timeToLoad = measureTime {
//            runBlocking {
//                persistenceManager.loadAllVehicles().collect { veh ->
//                    newMap[veh.id] = veh
//                }
//            }
//        }
//        logger.info("Loaded all vehicles in ${timeToLoad.toDouble(DurationUnit.MILLISECONDS)} ms")
//        lock.lock()
//        map.clear()
//        map.putAll(newMap)
//        lock.unlock()
//        logger.info("Loaded!")
    }
}
