package lab7.client.presentation.cli.commands

import lab7.client.domain.RemoteCommandHandler

/**
 * Класс команды show
 */
class ShowCommand(handler: RemoteCommandHandler) : CommandImpl(
    "show",
    "вывести в стандартный поток вывода все элементы коллекции в строковом представлении",
    "",
    fun(_, writer, _, _, _) {
        val list = handler.show()
        require(list.size <= MAX_CARS_NUMBER) { "Запрошено слишком много машин (${list.size})" }
        if (list.isEmpty()) {
            writer.write("В коллекции нет элементов\n")
        } else {
            list.forEach { writer.write(it.toString() + "\n") }
        }
        writer.flush()
    },
)

const val MAX_CARS_NUMBER = 100
