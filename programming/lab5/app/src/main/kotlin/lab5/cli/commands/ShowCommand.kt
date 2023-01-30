package lab5.cli.commands

import lab5.repositories.VehicleRepository
import java.io.BufferedWriter

/**
 * Класс команды show
 */
class ShowCommand(repository: VehicleRepository, writer: BufferedWriter): CommandImpl(
    "show",
    "вывести в стандартный поток вывода все элементы коллекции в строковом представлении",
    "",
    fun (_, _, _, _) {
        if(repository.getCollectionInfo().elementsCount == 0) {
            writer.write("В коллекции нет элементов\n")
        }
        else {
            for (item in repository.listAllVehicles()) {
                writer.write("$item\n")
            }
        }
        writer.flush()
    },
    )