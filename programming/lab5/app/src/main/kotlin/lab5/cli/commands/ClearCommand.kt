package lab5.cli.commands

import lab5.repositories.VehicleRepository

/**
 * Класс команды clear.
 * Очищает коллекцию от всех элементов
 */
class ClearCommand(repository: VehicleRepository) : CommandImpl(
    "clear",
    "очистить коллекцию",
    "",
    fun(_, writer, _, _, _) {
        repository.clear()
        writer.write("Коллекция очищена\n")
        writer.flush()
    },
)