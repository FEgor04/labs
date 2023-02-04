package lab5.cli.commands

import lab5.repositories.VehicleRepository
import java.io.BufferedWriter

/**
 * Класс команды show
 */
class MinByIdCommand(repository: VehicleRepository): CommandImpl(
    "min_by_id",
    "вывести любой объект из коллекции, значение поля id которого является минимальным",
    "",
    fun (_, writer, _, _) {
        val element = repository.getMinById()
        if(element == null) {
            writer.write("В коллекции нет элементов.\n")
        }
        else {
            writer.write("$element\n")
        }
        writer.flush()
    },
    )