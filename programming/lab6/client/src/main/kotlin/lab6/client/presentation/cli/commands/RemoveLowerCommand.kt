package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler
import lab6.client.presentation.cli.utils.ReaderUtils

/**
 * Класс команды remove_lower.
 * Использование: remove_lower {element}
 * Удаляет все элементы коллекции, чей ID больше, чем данный
 */
class RemoveLowerCommand(handler: RemoteCommandHandler) : CommandImpl(
    "remove_lower",
    "удалить из коллекции все элементы, меньшие, чем заданный",
    "",
    fun(_, writer, reader, _, _) {
        val element = ReaderUtils.readVehicle(writer, reader)
        try {
            val cnt = handler.removeLower(element)
            writer.write("Успех! Удалено $cnt элементов\n")
        }
        catch (e: Exception) {
            writer.write("Ошибка $e\n")
        }
        writer.flush()
    },
)