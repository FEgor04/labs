package lab5.cli.commands

import lab5.repositories.VehicleRepository
import java.io.BufferedWriter

/**
 * Класс команды save
 */
class SaveCommand(repository: VehicleRepository): CommandImpl(
    "save",
    "сохранить коллекцию в файл",
    "",
    fun (_, writer, _, _) {
        try {
            repository.saveCollection()
            writer.write("Успех!")
            writer.flush()
        }
        catch(e: Exception) {
            writer.write("Не удалось сохранить коллекцию. Ошибка: $e\n")
            writer.flush()
        }
    },
    )