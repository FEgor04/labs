package lab6.client.presentation.cli.commands

import lab6.client.presentation.cli.utils.ReaderUtils
import lab6.client.domain.RemoteCommandHandler

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
        try {
            val response = handler.removeGreater(element)
            writer.write("Удалено $response элементов\n")
        }
        catch (e: Exception) {
            writer.write("Ошибка: $e\n")
        }
        writer.flush()
    },
)