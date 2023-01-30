package lab5.cli.commands

import lab5.cli.utils.ReaderUtils
import lab5.repositories.VehicleRepository
import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Класс команды remove_lower.
 * Использование: remove_lower {element}
 * Удаляет все элементы коллекции, чей ID больше, чем данный
 */
class RemoveLowerCommand(repository: VehicleRepository, writer: BufferedWriter, reader: BufferedReader): CommandImpl(
    "remove_lower",
    "удалить из коллекции все элементы, меньшие, чем заданный",
    "",
    fun (_, _, _, _) {
        val element = ReaderUtils.readVehicle(writer, reader)
        repository.removeLower(element)
        writer.write("Успех!\n")
        writer.flush()
    },
)