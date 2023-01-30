package lab5.cli.commands

import lab5.repositories.VehicleRepository
import java.io.BufferedWriter

/**
 * Класс команды clear
 * Очищает коллекцию от всех элементов
 */
class ClearCommand(repository: VehicleRepository, writer: BufferedWriter): CommandImpl(
    "clear",
    "очистить коллекцию",
    "",
    fun (_, _, _, _) {
        repository.clear()
        writer.write("Коллекция очищена\n")
        writer.flush()
    },
    )