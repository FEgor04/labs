package lab5.entities.vehicle

import lab5.entities.FactoryException
import lab5.entities.ValidationException

/**
 * Синглтон VehicleFactory
 */
object VehicleFactory {
    /**
     * Создает транспорт из строки.
     * Чтобы оставить поле равным null можно либо указать null, либо оставить строку пустой.
     */
    fun createVehicleFromString(
        name: String,
        xStr: String,
        yStr: String,
        enginePowerStr: String,
        vehicleType: String,
        fuelType: String
    ): Vehicle {
        val x: Int
        val y: Long?
        val enginePower: Double
        val vehType: VehicleType?

        if(name.isEmpty()) {
            throw FactoryException("name", "should not be empty")
        }

        try {
            x = xStr.trim().toInt()
        }
        catch (e: NumberFormatException) {
            throw FactoryException("x", "should be an int")
        }

        if(yStr.trim() != "null" && yStr.isNotEmpty()) {
            try {
                y = yStr.trim().toLong()
            } catch (e: NumberFormatException) {
                throw FactoryException("y", "should be an int (long)")
            }
        }
        else {
            y = null
        }

        try {
            enginePower = enginePowerStr.trim().toDouble()
        }
        catch (e: NumberFormatException) {
            throw FactoryException("y", "should be a real number")
        }

        if(vehicleType == "null" || vehicleType.isEmpty()) {
            vehType = null
        }
        else {
            vehType = VehicleType.values().find { it.toString().lowercase() == vehicleType.trim().lowercase() }
            if (vehType == null) {
                throw FactoryException("vehicle type", "should be one of ${VehicleType.values().map { "$it" }}")
            }
        }

        val fType = FuelType.values().find { it.toString().lowercase() ==  fuelType.trim().lowercase()}
        if(fType == null) {
            throw FactoryException("fuel type", "should be one of ${FuelType.values().map { "$it" }}")
        }

        val vehicle = Vehicle(1, name, Coordinates(x, y), java.time.LocalDate.MIN, enginePower, vehType, fType)
        try {
            vehicle.validate()
        }
        catch(e: ValidationException) {
            throw e
        }

        return Vehicle(0, name, Coordinates(x, y), java.time.LocalDate.now(), enginePower, vehType, fType)
    }

    /**
     * Создает рандомный транспорт
     */
    fun generateRandomVehicle(): Vehicle {
        val random = kotlin.random.Random(System.currentTimeMillis())
        return Vehicle(
            id = random.nextInt(1, 100000),
            name = "Test ${random.nextInt()}",
            coordinates = Coordinates(random.nextInt(-522, 1000000), random.nextLong()),
            creationDate = java.time.LocalDate.now(),
            enginePower = random.nextDouble(1.0, 1000000.0),
            fuelType = FuelType.values()[random.nextInt(0, FuelType.values().size)],
            type = VehicleType.values()[random.nextInt(0, VehicleType.values().size)]
        )
    }
}