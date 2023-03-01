package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler

/**
 * Класс команды show
 */
class ShowCommand(handler: RemoteCommandHandler) : CommandImpl(
    "show",
    "вывести в стандартный поток вывода все элементы коллекции в строковом представлении",
    "",
    fun(_, writer, _, _, _) {
        try {
            val list = handler.show()
            if(list.size == 0) {
                writer.write("В коллекции нет элементов\n")
            }
            else {
                list.forEach { writer.write(it.toString() + "\n") }
            }
        }
        catch(e: Exception) {
            writer.write("Ошибка $e\n")
        }
        writer.flush()
    },
)