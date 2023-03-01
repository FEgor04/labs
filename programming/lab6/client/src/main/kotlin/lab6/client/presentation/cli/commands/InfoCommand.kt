package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler

/**
 * Класс команды info
 */
class InfoCommand(handler: RemoteCommandHandler) : CommandImpl(
    "info",
    "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)",
    "",
    fun(_, writer, _, _, _) {
        writer.write(handler.getCollectionInfo().toString() + "\n")
        writer.flush()
    },
)