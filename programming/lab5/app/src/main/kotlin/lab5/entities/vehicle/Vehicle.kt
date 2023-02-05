package lab5.entities.vehicle

import lab5.entities.FactoryException
import lab5.entities.ValidationException

/**
 * Дата-класс транспорта
 */
data class Vehicle(
    val id: Int,
    val name: String,
    val coordinates: Coordinates,
    val creationDate: java.time.LocalDate,
    val enginePower: Double,
    val type: VehicleType?,
    val fuelType: FuelType,
) {
    /**
     * Функция, валидирующая Vehicle
     * @throws ValidationException если хотя бы одно из полей невалидно
     */
    fun validate() {
        if (id <= 0) {
            throw ValidationException("id", "should be greater than 0")
        }
        if (name.isEmpty()) {
            throw ValidationException("name", "should not be empty")
        }
        if (enginePower <= 0) {
            throw ValidationException("enginePower", "should be greater than 0")
        }
        coordinates.validate()
    }

    /**
     * Конвертирует в CSV
     */
    fun toCsv(): String {
        return "$id,${
            name.replace(
                ",",
                "\\,"
            )
        },${coordinates.x},${coordinates.y},$creationDate,$enginePower,$type,$fuelType"
    }

    /**
     * Реализация паттерна Static Factory Method
     */
    companion object {
        /**
         * Создает Vehicle из переданных строк.
         * Значение считается null если оно равно null либо пустое.
         */
        fun createVehicleFromString(
            name: String,
            xStr: String,
            yStr: String,
            enginePowerStr: String,
            vehicleType: String,
            fuelType: String
        ): Vehicle {

            if (name.isEmpty()) {
                throw FactoryException("name", "should not be empty")
            }

            val x: Int
            try {
                x = xStr.trim().toInt()
            } catch (e: NumberFormatException) {
                throw FactoryException("x", "should be an int")
            }

            val y: Long? = if (yStr.trim() == "null" || yStr.isEmpty()) {
                null
            } else {
                try {
                    yStr.trim().toLong()
                } catch (e: NumberFormatException) {
                    throw FactoryException("y", "should be an int (long)")
                }
            }

            val enginePower: Double
            try {
                enginePower = enginePowerStr.trim().toDouble()
            } catch (e: NumberFormatException) {
                throw FactoryException("y", "should be a real number")
            }

            val vehType: VehicleType?
            if (vehicleType.trim() == "null" || vehicleType.isEmpty()) {
                vehType = null
            } else {
                vehType = VehicleType.values().find { it.toString().lowercase() == vehicleType.trim().lowercase() }
                if (vehType == null) {
                    throw FactoryException("vehicle type", "should be one of ${VehicleType.values().map { "$it" }}")
                }
            }

            val fType = FuelType.values().find { it.toString().lowercase() == fuelType.trim().lowercase() }
                ?: throw FactoryException("fuel type", "should be one of ${FuelType.values().map { "$it" }}")

            val vehicle = Vehicle(1, name, Coordinates(x, y), java.time.LocalDate.MIN, enginePower, vehType, fType)
            try {
                vehicle.validate()
            } catch (e: ValidationException) {
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
}