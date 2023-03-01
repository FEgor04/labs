package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler

/**
 * Класс команды remove_key
 */
class RemoveKeyCommand(handler: RemoteCommandHandler) : CommandImpl(
    "remove_key",
    "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)",
    "(\\d+)",
    fun(userInput, writer, _, _, regex) {
        val id: Int
        try {
            val (idStr) = regex.find(userInput)?.destructured!!
            id = idStr.toInt()
        } catch (e: Exception) {
            writer.write("Неправильный ввод.\n")
            writer.flush()
            return
        }
        try {
            handler.remove(id)
            writer.write("Успех!\n")
        }
        catch(e: Exception) {
            writer.write("Ошибка $e\n")
        }
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}