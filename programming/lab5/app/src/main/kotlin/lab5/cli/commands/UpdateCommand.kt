package lab5.cli.commands

import lab5.cli.utils.ReaderUtils
import lab5.entities.vehicle.FuelType
import lab5.entities.vehicle.VehicleType
import lab5.repositories.VehicleRepository

/**
 * Класс команды update
 */
class UpdateCommand(repository: VehicleRepository) : CommandImpl(
    "update",
    "обновить значение элемента коллекции, id которого равен заданному",
    "\\d*",
    fun(userInput, writer, reader, _) {
        val regex = Regex("update\\s(\\d*)")
        val id: Int
        try {
            val (idStr) = regex.find(userInput)?.destructured!!
            id = idStr.toInt()
        } catch (e: Exception) {
            writer.write("Неправильный ввод. Попробуйте еще.\n")
            writer.flush()
            return
        }
        val vehicle = repository.getVehicleById(id)
        if (vehicle == null) {
            writer.write("Нет такого автомобиля. Сначала добавь\n")
            writer.flush()
            return
        }
        val newName: String = (ReaderUtils.readType(reader, writer,
            hint = "Введите новое имя или оставьте пустым чтобы оставить старое значение = ${vehicle.name}.\n",
            caster = { it.ifEmpty { null } },
            validator = { true }
        )) ?: vehicle.name

        val newX: Int = (ReaderUtils.readType(reader, writer,
            hint = "Введите новое значение координаты X или оставьте пустым чтобы оставить старое значение = ${vehicle.coordinates.x}\n",
            caster = ReaderUtils.toIntOrNullCaster,
            validator = { it == null || it > -523 }
        )) ?: vehicle.coordinates.x

        val newY: Long? = ReaderUtils.readType(reader, writer,
            hint = "Введите новое значение координаты Y или оставьте пустым чтобы ввести null\n",
            caster = ReaderUtils.toLongOrNullCaster,
            validator = { true }
        )

        val newEnginePower: Double = ReaderUtils.readType(reader, writer,
            hint = "Введите новое значение мощности двигателя или оставьте пустым чтобы оставить старое значение = ${vehicle.enginePower}\n",
            caster = ReaderUtils.toDoubleOrNullCaster,
            validator = { true }
        ) ?: vehicle.enginePower

        val newVehicleType: VehicleType? = ReaderUtils.readType(reader, writer,
            hint = "Введите новое значение типа транспорта (${
                VehicleType.values().map { it.toString() }
            }) или оставьте пустым для null.\n",
            caster = { ReaderUtils.toEnumOrNullCaster<VehicleType>(it) },
            validator = { true }
        )

        val newFuelType: FuelType = ReaderUtils.readType(reader, writer,
            hint = "Введите новое значение типа топлива (${
                FuelType.values().map { it.toString() }
            }) или оставьте пустым для старого значения = ${vehicle.fuelType}\n",
            caster = { ReaderUtils.toEnumOrNullCaster<FuelType>(it) },
            validator = { true }
        ) ?: vehicle.fuelType

        val newVehicle = vehicle.copy(
            name = newName,
            coordinates = vehicle.coordinates.copy(x = newX, y = newY),
            enginePower = newEnginePower,
            fuelType = newFuelType,
            type = newVehicleType
        )
        repository.updateVehicleById(vehicle = newVehicle)
        writer.write("Успех!\n")
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}
