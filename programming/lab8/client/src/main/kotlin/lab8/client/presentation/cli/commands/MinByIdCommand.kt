package lab8.client.presentation.cli.commands

import lab8.client.domain.RemoteCommandHandler
import lab8.client.presentation.cli.writeln

/**
 * Класс команды show
 */
class MinByIdCommand(handler: RemoteCommandHandler) : CommandImpl(
    "min_by_id",
    "вывести любой объект из коллекции, значение поля id которого является минимальным",
    "",
    fun(_, writer, _, _, _) {
        val element = handler.getMinById()
        if (element == null) {
            writer.writeln("В коллекции нет элементов")
        } else {
            writer.writeln(element.toString())
        }
        writer.flush()
    },
)
