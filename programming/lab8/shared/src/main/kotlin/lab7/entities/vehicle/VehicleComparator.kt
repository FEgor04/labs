package lab7.entities.vehicle

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
