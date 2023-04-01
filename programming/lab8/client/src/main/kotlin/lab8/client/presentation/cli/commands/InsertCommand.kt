package lab8.client.presentation.cli.commands

import lab8.client.domain.RemoteCommandHandler
import lab8.client.presentation.cli.utils.ReaderUtils

/**
 * Класс команды insert
 */
class InsertCommand(handler: RemoteCommandHandler) : CommandImpl(
    "insert",
    "добавить новый элемент с заданным ключом",
    "",
    fun(_, writer, reader, _, _) {
        val vehicle = ReaderUtils.readVehicle(writer, reader)
        val id = handler.add(vehicle)
        writer.write("Новый элемент добавлен с ID = $id\n")
        writer.flush()
    },
)
