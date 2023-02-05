package lab5.cli.commands

import lab5.repositories.VehicleRepository

/**
 * Класс команды show
 */
class ShowCommand(repository: VehicleRepository) : CommandImpl(
    "show",
    "вывести в стандартный поток вывода все элементы коллекции в строковом представлении",
    "",
    fun(_, writer, _, _, _) {
        if (repository.getCollectionInfo().elementsCount == 0) {
            writer.write("В коллекции нет элементов\n")
        } else {
            repository.forEach { writer.write("$it\n") }
        }
        writer.flush()
    },
)