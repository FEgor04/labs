package lab5.cli.commands

import lab5.cli.utils.ReaderUtils
import lab5.repositories.VehicleRepository

/**
 * Класс команды remove_lower.
 * Использование: remove_lower {element}
 * Удаляет все элементы коллекции, чей ID больше, чем данный
 */
class RemoveLowerCommand(repository: VehicleRepository) : CommandImpl(
    "remove_lower",
    "удалить из коллекции все элементы, меньшие, чем заданный",
    "",
    fun(_, writer, reader, _) {
        val element = ReaderUtils.readVehicle(writer, reader)
        repository.removeLower(element)
        writer.write("Успех!\n")
        writer.flush()
    },
)