package lab5.cli.utils

import lab5.entities.vehicle.Coordinates
import lab5.entities.vehicle.FuelType
import lab5.entities.vehicle.Vehicle
import lab5.entities.vehicle.VehicleType
import java.io.BufferedReader
import java.io.BufferedWriter
import java.time.LocalDate
import java.util.function.Predicate

/**
 * Тип функции, приводящей строку ввода к типу T
 * @param T желаемый тип
 */
typealias TypeCaster<T> = (userInput: String) -> T

/**
 * Вспомогательный объект для чтения консоли
 */
object ReaderUtils {
    /**
     * Функция, читающий тип T из reader и выводящая подсказки в writer
     * @param hint подсказка для ввода
     * @param caster функция, приводящая строку к необходимому типу
     * @param validator функция, проверяющая валидность введенного типа
     */
    fun <T> readType(
        reader: BufferedReader,
        writer: BufferedWriter,
        hint: String,
        caster: TypeCaster<T>,
        validator: Predicate<T>
    ): T {
        writer.write(hint)
        writer.flush()
        var output: T
        while (true) {
            val userInput = reader.readLine()
            try {
                output = caster(userInput)
            } catch (e: Exception) {
                when (e) {
                    is NumberFormatException -> {
                        writer.write("Некорректный ввод числа. Попробуйте еще.\n")
                        writer.flush()
                        continue
                    }

                    is IllegalArgumentException -> {
                        writer.write("Некорректный ввод enum.\n")
                        writer.flush()
                        continue
                    }

                    else -> {
                        writer.write("Некорректный ввод. Попробуйте еще.\n")
                        writer.flush()
                        continue
                    }
                }
            }
            if (validator.test(output)) {
                break
            } else {
                writer.write("Некорректный ввод. Попробуйте еще.\n")
                writer.flush()
            }
        }
        return output
    }

    val toIntCaster: TypeCaster<Int> = { it.trim().toInt() }
    val toIntOrNullCaster: TypeCaster<Int?> = {
        if (it.isEmpty()) {
            null
        } else {
            toIntCaster(it)
        }
    }
    val toLongCaster: TypeCaster<Long> = { it.trim().toLong() }
    val toLongOrNullCaster: TypeCaster<Long?> = {
        if (it.isEmpty()) {
            null
        } else {
            toLongCaster(it)
        }
    }
    val toDoubleCaster: TypeCaster<Double> = { it.trim().toDouble() }
    val toDoubleOrNullCaster: TypeCaster<Double?> = {
        if (it.isEmpty()) {
            null
        } else {
            toDoubleCaster(it)
        }
    }

    /**
     * Функция, приводящая строку ввода к enum-типу T
     * @param T желаемый enum тип
     * @throws IllegalArgumentException если введенного значения нет в T
     */
    inline fun <reified T : Enum<T>> toEnumCaster(userInput: String): T {
        return enumValueOf(userInput.trim().uppercase())
    }

    /**
     * Функция, приводящая строку ввода к enum-типу T или null, если была введена пустая строка
     * @param T желаемый enum тип
     * @throws IllegalArgumentException если введенного значения нет в T
     */
    inline fun <reified T : Enum<T>> toEnumOrNullCaster(userInput: String): T? {
        if (userInput.isEmpty()) {
            return null
        }
        return toEnumCaster<T>(userInput)
    }

    /**
     * Функция, читающая все поля Vehicle
     */
    fun readVehicle(writer: BufferedWriter, reader: BufferedReader): Vehicle {
        val name: String = readType(
            reader, writer,
            hint = "Введите название транспорта.\n",
            caster = { it },
            validator = { it.isNotEmpty() },
        )
        val x: Int = readType(reader, writer,
            hint = "Введите переменную X > -523\n",
            caster = toIntCaster,
            validator = { it > -523 })
        val y: Long? = readType(reader, writer,
            hint = "Введите переменную Y\n",
            caster = toLongOrNullCaster,
            validator = { true })
        val enginePower: Double = readType(reader, writer,
            hint = "Введите мощность двигателя > 0\n",
            caster = toDoubleCaster,
            validator = { it > 0 })
        val vehicleType: VehicleType? = readType(reader, writer,
            hint = "Введите тип транспорта. Возможные значения: ${
                VehicleType.values().map { it.toString() }
            } или оставьте строку пустой для null\n",
            caster = { toEnumOrNullCaster<VehicleType>(it) },
            validator = { true })
        val fuelType: FuelType = readType(reader, writer,
            hint = "Введите тип топлива. Возможные значения: ${FuelType.values().map { it.toString() }}\n",
            caster = { toEnumCaster(it) },
            validator = { true })

        return Vehicle(
            1,
            name,
            coordinates = Coordinates(x, y),
            enginePower = enginePower,
            fuelType = fuelType,
            type = vehicleType,
            creationDate = LocalDate.now()
        )
    }
}