package lab7.lb.data.cache.treemap

import lab7.entities.CollectionInfo
import lab7.entities.vehicle.Vehicle
import lab7.entities.vehicle.VehicleType
import lab7.lb.domain.cache.Cache
import java.time.LocalDate
import java.util.*
import java.util.Collections.synchronizedMap

class TreeMapCache: Cache {
    val map = synchronizedMap(TreeMap<Int, Vehicle>());

    override fun show(): List<Vehicle> {
        return map.map { it.value }.toList()
    }

    override fun countByType(type: VehicleType?): Int {
        return map.count { it.value.type == type }
    }

    override fun countLessThanEnginePower(power: Double): Int {
        return map.count { it.value.enginePower < power }
    }

    override fun info(): CollectionInfo {
        return CollectionInfo(
            map.size,
            LocalDate.now(),
            "CACHE ON LOAD BALANCER",
        )
    }

    override fun getMinByID(): Vehicle? {
        return map.minByOrNull { it.key }?.value
    }

    override fun synchronize(vehicles: List<Vehicle>) {
        map.clear()
        vehicles.forEach { veh ->
            map[veh.id] = veh
        }
    }
}