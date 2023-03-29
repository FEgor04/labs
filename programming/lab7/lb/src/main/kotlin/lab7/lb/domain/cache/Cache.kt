package lab7.lb.domain.cache

import lab7.entities.CollectionInfo
import lab7.entities.vehicle.Vehicle
import lab7.entities.vehicle.VehicleType

interface Cache {
    fun show(): List<Vehicle>
    fun countByType(type: VehicleType?): Int
    fun countLessThanEnginePower(power: Double): Int
    fun info(): CollectionInfo
    fun getMinByID(): Vehicle?

    fun synchronize(vehicles: List<Vehicle>)
}