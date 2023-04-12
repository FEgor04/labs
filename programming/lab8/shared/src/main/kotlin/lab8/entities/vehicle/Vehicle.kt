package lab8.entities.vehicle

import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

/**
 * Дата-класс транспорта
 */

@Serializable
data class Vehicle(
    val id: Int,
    val name: String,
    val coordinates: Coordinates,
    @Serializable(LocalDateSerializer::class) val creationDate: LocalDate,
    val enginePower: Double,
    val type: VehicleType?,
    val fuelType: FuelType,
    val authorID: Int
) : Comparable<Vehicle> {
    /**
     * Функция, валидирующая Vehicle
     * @throws ValidationException если хотя бы одно из полей невалидно
     */
    fun validate() {
        validateId()
        validateName()
        validateEnginePower()
        coordinates.validate()
    }

    private fun validateId() {
        if (id <= idLowerBound) {
            throw ValidationException("id", "should be greater than 0")
        }
    }

    private fun validateName() {
        if (name.isEmpty()) {
            throw ValidationException("name", "should not be empty")
        }
    }

    private fun validateEnginePower() {
        if (enginePower <= enginePowerLowerBound) {
            throw ValidationException("enginePower", "should be greater than 0")
        }
    }

    /**
     * Реализация паттерна Static Factory Method
     */
    companion object {
        /**
         * Создает рандомный транспорт
         */
        fun generateRandomVehicle(random: kotlin.random.Random = kotlin.random.Random(System.nanoTime())): Vehicle {
            return Vehicle(
                id = random.nextInt(idLowerBound + 1, Int.MAX_VALUE),
                name = "Test ${random.nextInt(0, 100)}",
                coordinates = Coordinates(
                    random.nextInt(Coordinates.xLowerBound + 1, 100),
                    random.nextLong(-100, 100)
                ),
                creationDate = LocalDate.now(),
                enginePower = random.nextDouble(enginePowerLowerBound + 1.0, 100.0),
                fuelType = FuelType.values()[random.nextInt(0, FuelType.values().size)],
                type = VehicleType.values()[random.nextInt(0, VehicleType.values().size)],
                authorID = random.nextInt(0, 200)
            )
        }

        const val idLowerBound = 0
        const val enginePowerLowerBound = 0
    }

    fun getAllFields(locale: Locale = Locale.getDefault()): List<String> {
        val dateFormat = DateTimeFormatter.ofPattern("d MMM uuuu", locale)
        val numberFormat = NumberFormat.getInstance(locale)
        return listOf(
            this.id,
            this.name,
            this.coordinates,
            dateFormat.format(this.creationDate),
            numberFormat.format(this.enginePower),
            this.type,
            this.fuelType,
            this.authorID
        ).map {
            it.toString()
        }
    }

    override fun compareTo(other: Vehicle): Int {
        return VehicleComparator().compare(this, other)
    }
}
