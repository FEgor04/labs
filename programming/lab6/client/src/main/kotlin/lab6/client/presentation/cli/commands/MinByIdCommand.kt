package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler
import lab6.client.presentation.cli.commands.CommandImpl

/**
 * Класс команды show
 */
class MinByIdCommand(handler: RemoteCommandHandler) : CommandImpl(
    "min_by_id",
    "вывести любой объект из коллекции, значение поля id которого является минимальным",
    "",
    fun(_, writer, _, _, _) {
        try {
            val element = handler.getMinById()
            if(element == null) {
                writer.write("В коллекции нет элементов\n")
            }
            else {
                writer.write(element.toString() + "\n")
            }
        }
        catch (e: Exception) {
            writer.write("Ошибка $e\n")
        }
        writer.flush()
    },
)