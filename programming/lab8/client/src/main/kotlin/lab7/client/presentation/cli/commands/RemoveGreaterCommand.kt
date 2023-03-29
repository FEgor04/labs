package lab7.client.presentation.cli.commands

import lab7.client.domain.RemoteCommandHandler
import lab7.client.presentation.cli.utils.ReaderUtils

/**
 * Класс команды remove_greater.
 * Использование: remove_greater
 * Удаляет все элементы коллекции, превыщающие данный
 */
class RemoveGreaterCommand(handler: RemoteCommandHandler) : CommandImpl(
    "remove_greater",
    "удалить из коллекции все элементы, превышающие данный",
    "",
    fun(_, writer, reader, _, _) {
        val element = ReaderUtils.readVehicle(writer, reader)
        val response = handler.removeGreater(element)
        writer.write("Удалено $response элементов\n")
        writer.flush()
    },
)
