package lab5.cli.commands

import lab5.repositories.VehicleRepository
import java.io.BufferedWriter

/**
 * Класс команды info
 */
class InfoCommand(repository: VehicleRepository): CommandImpl(
    "info",
    "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)",
    "",
    fun (_, writer, _, _) {
        writer.write(repository.getCollectionInfo().toString() + "\n")
        writer.flush()
    },
    )