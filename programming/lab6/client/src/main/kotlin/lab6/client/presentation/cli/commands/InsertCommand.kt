package lab6.client.presentation.cli.commands

import lab6.client.presentation.cli.utils.ReaderUtils
import lab6.client.domain.RemoteCommandHandler
import lab6.client.presentation.cli.commands.CommandImpl

/**
 * Класс команды insert
 */
class InsertCommand(handler: RemoteCommandHandler) : CommandImpl(
    "insert",
    "добавить новый элемент с заданным ключом",
    "",
    fun(_, writer, reader, _, _) {
        val vehicle = ReaderUtils.readVehicle(writer, reader)
        try {
            val id = handler.add(vehicle)
            writer.write("Новый элемент добавлен с ID = $id\n")
        }
        catch (e: Exception) {
            writer.write("Ошибка $e\n")
        }
        writer.flush()
    },
)
