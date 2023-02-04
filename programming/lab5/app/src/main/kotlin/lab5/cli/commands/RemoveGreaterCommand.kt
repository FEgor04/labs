package lab5.cli.commands

import lab5.cli.utils.ReaderUtils
import lab5.repositories.VehicleRepository

/**
 * Класс команды remove_greater.
 * Использование: remove_greater
 * Удаляет все элементы коллекции, превыщающие данный
 */
class RemoveGreaterCommand(repository: VehicleRepository) : CommandImpl(
    "remove_greater",
    "удалить из коллекции все элементы, превышающие данный",
    "",
    fun(_, writer, reader, _) {
        val element = ReaderUtils.readVehicle(writer, reader)
        repository.removeGreater(element)
        writer.write("Успех!\n")
        writer.flush()
    },
)