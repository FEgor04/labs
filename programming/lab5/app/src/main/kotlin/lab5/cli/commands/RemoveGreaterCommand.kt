package lab5.cli.commands

import lab5.cli.utils.ReaderUtils
import lab5.entities.vehicle.FuelType
import lab5.entities.vehicle.VehicleType
import lab5.repositories.VehicleRepository
import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Класс команды remove_greater.
 * Использование: remove_greater
 * Удаляет все элементы коллекции, чей ID больше, чем данный
 */
class RemoveGreaterCommand(repository: VehicleRepository, writer: BufferedWriter, reader: BufferedReader): CommandImpl(
    "remove_greater",
    "удалить из коллекции все элементы, превышающие данный",
    "",
    fun (_, _, _, _) {
        val element = ReaderUtils.readVehicle(writer, reader)
        repository.removeGreater(element)
        writer.write("Успех!\n")
        writer.flush()
    },
)