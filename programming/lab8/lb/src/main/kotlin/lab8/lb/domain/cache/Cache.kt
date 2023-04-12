package lab8.lb.domain.cache

import lab8.entities.CollectionInfo
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType

interface Cache {
    fun show(): List<Vehicle>
    fun countByType(type: VehicleType?): Int
    fun countLessThanEnginePower(power: Double): Int
    fun info(): CollectionInfo
    fun getMinByID(): Vehicle?

    fun synchronize(vehicles: List<Vehicle>)
}
