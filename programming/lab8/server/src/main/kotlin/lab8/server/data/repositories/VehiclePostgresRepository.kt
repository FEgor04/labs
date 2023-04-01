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
    private val map = TreeMap<Int, Vehicle>()
    private val creationDate = LocalDate.now()
    private val logger by KCoolLogger()
    private val lock = ReentrantLock()

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
        lock.lock()
        val ans = map
            .toList()
            .parallelStream()
            .map { it.component2() }
            .collect(Collectors.toList())
        lock.unlock()
        return ans
    }

    override fun countByType(type: VehicleType?): Int {
        lock.lock()
        val ans = this.map
            .filter { it.component2().type == type }.size
        lock.unlock()
        return ans
    }

    override suspend fun clear(user: User) {
        this.map
            .filter { it.value.authorID == user.id }
            .forEach { map.remove(it.key) }
        this.persistenceManager.clear(user)
        this.load()
    }

    override fun countLessThanEnginePower(power: Double): Int {
        lock.lock()
        val vehicles = this.map.filter { it.component2().enginePower < power }
        vehicles.forEach { this.map.remove(it.component1()) }
        lock.unlock()
        return vehicles.size
    }

    override fun getCollectionInfo(): CollectionInfo {
        lock.lock()
        val date = this.creationDate
        val ans = CollectionInfo(
            size = map.size,
            creationDate = date,
            type = map.javaClass.typeName
        )
        lock.unlock()
        return ans
    }

    override fun getMinById(): Vehicle? {
        lock.lock()
        if (map.size == 0) {
            return null
        }
        val ans = this.map.firstEntry().component2()
        lock.unlock()
        return ans
    }

    override suspend fun removeGreater(vehicle: Vehicle, author: User): Int {
        persistenceManager.removeGreater(vehicle, author)
        logger.info(
            "AWAITING FOR LOCK FOR Deleting vehicles greater than ${vehicle.enginePower} and author is ${author.id}"
        )
        lock.lock()
        logger.info("Locked! Starting to delete")
        val count: Int
        this.map.values
            .filter { (it > vehicle) and (it.authorID == author.id) }
            .also { count = it.size }
            .forEach { map.remove(it.id) }
        lock.unlock()
        logger.info("Lock n' load")
        this.load()
        return count
    }

    override suspend fun remove(id: Int, author: User) {
        persistenceManager.remove(id, author)
        lock.lock()
        this.map.remove(id)
        lock.unlock()
        this.load()
    }

    override suspend fun removeLower(vehicle: Vehicle, author: User): Int {
        persistenceManager.removeLower(vehicle, author)
        lock.lock()
        val vehicles = this.map.values.filter { (it < vehicle) and (it.authorID == author.id) }
        vehicles.forEach { this.map.remove(it.id) }
        lock.unlock()
        this.load()
        return vehicles.size
    }

    @Suppress("ReturnCount")
    override suspend fun replaceIfLower(id: Int, veh: Vehicle, author: User): ReplaceIfLowerResults {
        persistenceManager.replaceIfLower(id, veh, author)
        if (this.map.containsKey(id)) {
            val before = this.map[id]
            if (before != null && veh < before) {
                if (before.authorID != author.id) {
                    throw ServerException.BadOwnerException()
                }
                this.lock.lock()
                this.map[id] = veh.copy(id = id)
                this.lock.unlock()
                this.load()
                return ReplaceIfLowerResults.REPLACED
            }
            return ReplaceIfLowerResults.NOT_REPLACED
        } else {
            return ReplaceIfLowerResults.NOT_EXISTS
        }
    }

    @Suppress("TooGenericExceptionThrown")
    override suspend fun updateVehicleById(newVehicle: Vehicle, author: User) {
        logger.info("Updating vehicle#${newVehicle.id}")
        val oldVeh = this.map[newVehicle.id]
        logger.info("got old veh: $oldVeh")
        if (oldVeh == null) {
            add(newVehicle, author)
            return
        }
        if (oldVeh.authorID == author.id) {
            logger.info("Updating in persistance manager")
            persistenceManager.update(newVehicle, author)
            this.load()
            this.lock.lock()
            this.map[newVehicle.id] = newVehicle
            this.lock.unlock()
        } else {
            logger.warn("wrong author. throwing an exception")
            throw ServerException.BadOwnerException()
        }
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
