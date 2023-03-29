package lab7.client.presentation.cli.commands

import lab7.client.domain.RemoteCommandHandler

/**
 * Класс команды remove_key
 */
class RemoveKeyCommand(handler: RemoteCommandHandler) : CommandImpl(
    "remove_key",
    "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)",
    "(\\d+)",
    fun(userInput, writer, _, _, regex) {
        val id: Int
        val (idStr) = regex.find(userInput)?.destructured!!
        id = idStr.toInt()
        handler.remove(id)
        writer.write("Успех!\n")
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}
