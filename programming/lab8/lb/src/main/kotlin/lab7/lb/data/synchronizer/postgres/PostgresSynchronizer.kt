package lab7.lb.data.synchronizer.postgres

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import lab7.entities.enumValueOfOrNull
import lab7.entities.vehicle.Coordinates
import lab7.entities.vehicle.FuelType
import lab7.entities.vehicle.Vehicle
import lab7.entities.vehicle.VehicleType
import lab7.lb.domain.synchornizer.CacheSynchronizer
import lab7.logger.KCoolLogger
import java.sql.Connection
import java.sql.SQLException
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class PostgresSynchronizer(val dataSource: HikariDataSource) : CacheSynchronizer {

    companion object {
        private const val getConnectionDelay = 1000
        private val logger by KCoolLogger()
    }

    override suspend fun synchronize(): List<Vehicle> {
        logger.info("Getting data from database.")
        val list = ArrayList<Vehicle>()
        val connection = dataSource.connection
        connection.use {
            val stm = connection.prepareStatement("SELECT * FROM vehicles")
            val res = withContext(Dispatchers.IO) { stm.executeQuery() }
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
                list.add(veh)
            }
        }
        logger.info("Got all data from database")
        return list
    }
}