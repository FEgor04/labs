package lab8.client.presentation.cli.commands

import lab8.client.domain.RemoteCommandHandler
import lab8.client.presentation.cli.utils.ReaderUtils

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
        val cnt = handler.removeLower(element)
        writer.write("Успех! Удалено $cnt элементов\n")
        writer.flush()
    },
)
