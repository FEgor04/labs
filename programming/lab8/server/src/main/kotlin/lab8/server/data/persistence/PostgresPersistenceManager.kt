package lab8.server.data.persistence

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lab8.entities.dtos.responses.ReplaceIfLowerResults
import lab8.entities.enumValueOfOrNull
import lab8.entities.user.User
import lab8.entities.vehicle.Coordinates
import lab8.entities.vehicle.FuelType
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType
import lab8.logger.KCoolLogger
import lab8.server.domain.persistence.PersistenceManager
import lab8.server.utilities.postgres.prepareStatementSuspend
import lab8.server.utilities.postgres.setArgs
import java.rmi.ServerException
import java.sql.Connection
import java.sql.SQLException
import kotlin.time.*

@Suppress("MagicNumber")
class PostgresPersistenceManager(private val connectionPool: HikariDataSource) : PersistenceManager {
    private val logger by KCoolLogger()
    private val getConnectionDelay: Duration = 10.toDuration(DurationUnit.MILLISECONDS)

    @OptIn(DelicateCoroutinesApi::class)
    private val databaseDispatcher = Dispatchers.IO

    @OptIn(ExperimentalTime::class)
    private suspend fun getConnection(): Connection {
        var connection: Connection
        val getConnectionTime = measureTime {
            var delay = getConnectionDelay
            while (true) {
                try {
                    connection = withContext(databaseDispatcher) {
                        connectionPool.connection
                    }
                    return@measureTime
                } catch (e: SQLException) {
                    logger.error("Could not get connection: $e! Retrying!")
                }
                delay(delay)
                delay = delay.times(2)
            }
        }
        logger.info("Got connection to database in $getConnectionTime")
        return connection
    }

    override suspend fun saveVehicle(veh: Vehicle, authorLogin: String): Int = withContext(databaseDispatcher) {
        logger.info("Saving new vehicle. Veh name: ${veh.name}")
        veh.validate()
        logger.debug("Validation succesful!")
        val connection = getConnection()
        connection.use {
            val insertStatement = connection.prepareStatementSuspend(
                """
                    INSERT INTO VEHICLES(name, x, y, engine_power, type, fuel, creator_id) 
                    VALUES (?, ?, ?, ?, ?::vehicle_type, ?::fuel_type,
                    (SELECT id FROM users WHERE users.name=?)) RETURNING id
                    """,
                databaseDispatcher
            )
            insertStatement.setArgs(
                veh.name,
                veh.coordinates.x,
                veh.coordinates.y,
                veh.enginePower,
                veh.type,
                veh.fuelType,
                authorLogin
            )
            val res = withContext(databaseDispatcher) { insertStatement.executeQuery() }
            logger.info("Query executed successfully. Getting results")
            res.next()
            val newId: Int = res.getInt(1)
            logger.info("New vehicle id is $newId")
            newId
        }
    }

    override fun loadAllVehicles(): Flow<Vehicle> = flow {
        val connection = getConnection()
        connection.use {
            val stm = connection.prepareStatementSuspend("SELECT * FROM vehicles", databaseDispatcher)
            val res = withContext(databaseDispatcher) { stm.executeQuery() }
            while (res.next()) {
                val veh = Vehicle(
                    id = res.getInt("id"),
                    name = res.getString("name"),
                    coordinates = Coordinates(
                        res.getInt("x"),
                        res.getLong("y")
                    ),
                    creationDate = res.getDate("creation_date").toLocalDate(),
                    enginePower = res.getDouble("engine_power"),
                    fuelType = FuelType.valueOf(res.getString("fuel")),
                    type = enumValueOfOrNull<VehicleType>(res.getString("type")),
                    authorID = res.getInt("creator_id")
                )
                emit(veh)
            }
        }
    }

    override suspend fun clear(user: User) {
        logger.info("Removing User#${user.id} vehicles from database")
        val connection = getConnection()
        connection.use {
            val stm =
                withContext(databaseDispatcher) {
                    connection.prepareStatementSuspend(
                        "DELETE FROM VEHICLES WHERE creator_id = ?",
                        databaseDispatcher
                    )
                }
            stm.setArgs(user.id)
            val cntDeleted = withContext(databaseDispatcher) { stm.executeUpdate() }
            logger.info("Deleted $cntDeleted vehicles.")
        }
    }

    override suspend fun removeGreater(vehicle: Vehicle, author: User): Int {
        logger.info("Removing all vehicles of User#${author.id} that are greater than $vehicle")
        val connection = getConnection()
        connection.use {
            val stm =
                connection.prepareStatementSuspend(
                    "DELETE FROM VEHICLES WHERE creator_id = ? AND engine_power > ?",
                    databaseDispatcher
                )
            stm.setArgs(author.id, vehicle.enginePower)
            val cntDeleted = withContext(databaseDispatcher) { stm.executeUpdate() }
            logger.info("Deleted $cntDeleted vehicles")
            return cntDeleted
        }
    }

    override suspend fun removeLower(veh: Vehicle, author: User): Int {
        logger.info("Removing all vehicles of User#${author.id} that are lower than $veh")
        val connection = getConnection()
        connection.use {
            val stm =
                connection.prepareStatementSuspend(
                    "DELETE FROM VEHICLES WHERE creator_id = ? AND engine_power < ?",
                    databaseDispatcher
                )
            stm.setArgs(author.id, veh.enginePower)
            val cntDeleted = withContext(databaseDispatcher) { stm.executeUpdate() }
            logger.info("Deleted $cntDeleted vehicles")
            return cntDeleted
        }
    }

    override suspend fun remove(id: Int, author: User) {
        logger.info("Removing vehicle of User#${author.id} which id is #$id")
        val connection = getConnection()
        connection.use {
            val stm = connection.prepareStatementSuspend(
                "DELETE FROM VEHICLES WHERE creator_id = ? AND id = ?",
                databaseDispatcher,
            )
            stm.setArgs(author.id, id)
            val cntDeleted = withContext(databaseDispatcher) { stm.executeUpdate() }
            logger.info("Deleted $cntDeleted vehicles")
        }
    }

    private suspend fun getVehicleById(id: Int): Vehicle? {
        logger.info("Getting vehicle #${id} from database")
        val connection = getConnection()
        connection.use {
            val stm = connection.prepareStatementSuspend(
                "SELECT * FROM VEHICLES WHERE id = ?",
                databaseDispatcher
            )
            stm.setArgs(id)
            val res = withContext(databaseDispatcher) {
                stm.executeQuery()
            }
            if(!res.next()) {
                return null
            }
            val veh = Vehicle(
                id = res.getInt("id"),
                name = res.getString("name"),
                coordinates = Coordinates(
                    res.getInt("x"),
                    res.getLong("y")
                ),
                creationDate = res.getDate("creation_date").toLocalDate(),
                enginePower = res.getDouble("engine_power"),
                fuelType = FuelType.valueOf(res.getString("fuel")),
                type = enumValueOfOrNull<VehicleType>(res.getString("type")),
                authorID = res.getInt("creator_id")
            )
            return veh
        }
    }

    @Suppress("ReturnCount")
    override suspend fun replaceIfLower(id: Int, veh: Vehicle, author: User): ReplaceIfLowerResults {
        logger.info("Replacing $id vehicle if it is ownwed by #${author.id} and is lower than $veh")
        val connection = getConnection()
        connection.use {
            val stm = connection.prepareStatementSuspend(
                "SELECT engine_power, creator_id FROM vehicles WHERE id = ?",
                databaseDispatcher
            )
            stm.setArgs(id)
            val res = withContext(databaseDispatcher) { stm.executeQuery() }
            if (!res.next()) {
                logger.info("Got nothing to replace")
                return ReplaceIfLowerResults.NOT_EXISTS
            }
            val beforeEnginePower = res.getDouble("engine_power")
            val creatorId = res.getInt("creator_id")
            if (creatorId != author.id) {
                throw lab8.exceptions.ServerException.BadOwnerException()
            }
            if (veh.enginePower < beforeEnginePower) {
                val stm1 = connection.prepareStatementSuspend(
                    """UPDATE vehicles SET name = ?,
                    |x = ?, y = ?, engine_power = ?, type = ?::vehicle_type,
                    |fuel = ?::fuel_type, creation_date = now() WHERE id = ? AND creator_id = ?
                    """.trimMargin(),
                    databaseDispatcher,
                )
                stm1.setArgs(
                    veh.name,
                    veh.coordinates.x,
                    veh.coordinates.y,
                    veh.enginePower,
                    veh.type,
                    veh.fuelType,
                    id,
                    author.id
                )
                withContext(databaseDispatcher) { stm1.execute() }
                logger.info("Replaced!")
                return ReplaceIfLowerResults.REPLACED
            }
            return ReplaceIfLowerResults.NOT_REPLACED
        }
    }

    override suspend fun update(newVehicle: Vehicle, user: User): Int {
        logger.info("Getting connection")
        val connection = getConnection()
        connection.use {
            val actualVeh = this.getVehicleById(newVehicle.id)
            if(actualVeh == null) {
                return 0
            }
            if(actualVeh.authorID != user.id) {
                throw lab8.exceptions.ServerException.BadOwnerException()
            }
            val stm1 = connection.prepareStatementSuspend(
                """UPDATE vehicles SET name = ?,
                    |x = ?, y = ?, engine_power = ?, type = ?::vehicle_type,
                    |fuel = ?::fuel_type, creation_date = now() WHERE id = ? AND creator_id = ?
                """.trimMargin(),
                databaseDispatcher,
            )
            stm1.setArgs(
                newVehicle.name,
                newVehicle.coordinates.x,
                newVehicle.coordinates.y,
                newVehicle.enginePower,
                newVehicle.type,
                newVehicle.fuelType,
                newVehicle.id,
                user.id
            )
            logger.info("Executing!")
            val cntUpdated = stm1.executeUpdate()
            logger.info("Executed!")
            return cntUpdated
        }
    }
}
