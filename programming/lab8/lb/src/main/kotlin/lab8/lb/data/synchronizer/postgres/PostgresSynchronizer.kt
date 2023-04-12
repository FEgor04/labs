package lab8.lb.data.synchronizer.postgres

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lab8.entities.enumValueOfOrNull
import lab8.entities.vehicle.Coordinates
import lab8.entities.vehicle.FuelType
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType
import lab8.lb.domain.synchornizer.CacheSynchronizer
import lab8.logger.KCoolLogger

class PostgresSynchronizer(private val dataSource: HikariDataSource) : CacheSynchronizer {

    companion object {
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
