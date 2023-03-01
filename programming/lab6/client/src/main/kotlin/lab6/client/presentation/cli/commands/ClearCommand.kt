package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler

/**
 * Класс команды clear.
 * Очищает коллекцию от всех элементов
 */
class ClearCommand(handler: RemoteCommandHandler) : CommandImpl(
    "clear",
    "очистить коллекцию",
    "",
    fun(_, writer, _, _, _) {
        try {
            handler.clear()
        } catch (e: Exception) {
            writer.write("Не удалось очистить коллекцию: $e")
            writer.flush()
            return
        }
        writer.write("Коллекция очищена\n")
        writer.flush()
    },
)