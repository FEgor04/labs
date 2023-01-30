package lab5.cli.commands

import lab5.repositories.VehicleRepository
import java.io.BufferedWriter

/**
 * Класс команды save
 */
class SaveCommand(repository: VehicleRepository, writer: BufferedWriter): CommandImpl(
    "save",
    "сохранить коллекцию в файл",
    "",
    fun (_, _, _, _) {
        try {
            repository.saveCollection()
            writer.write("Успех!")
            writer.flush()
        }
        catch(e: Exception) {
            writer.write("Could not save collection. Error: $e\n")
            writer.flush()
        }
    },
    )