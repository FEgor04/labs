package lab8.client.presentation.cli.commands

import lab8.client.domain.RemoteCommandHandler

/**
 * Класс команды clear.
 * Очищает коллекцию от всех элементов
 */
class ClearCommand(handler: RemoteCommandHandler) : CommandImpl(
    "clear",
    "очистить коллекцию",
    "",
    fun(_, writer, _, _, _) {
        handler.clear()
        writer.write("Коллекция очищена\n")
        writer.flush()
    },
)
