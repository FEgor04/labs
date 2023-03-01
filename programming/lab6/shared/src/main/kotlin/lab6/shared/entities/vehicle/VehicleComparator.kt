package lab6.shared.entities.vehicle

import lab6.shared.entities.vehicle.Vehicle

/**
 * Компаратор для транспорта
 */
class VehicleComparator : Comparator<Vehicle> {
    override fun compare(o1: Vehicle?, o2: Vehicle?): Int {
        if (o1 == null || o2 == null) {
            return 0
        }
        return o1.enginePower.compareTo(o2.enginePower)
    }

}