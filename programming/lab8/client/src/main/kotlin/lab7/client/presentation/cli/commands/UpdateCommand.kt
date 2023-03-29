package lab7.client.presentation.cli.commands

import lab7.client.domain.RemoteCommandHandler
import lab7.client.presentation.cli.utils.ReaderUtils

/**
 * Класс команды update
 */
class UpdateCommand(repository: RemoteCommandHandler) : CommandImpl(
    "update",
    "обновить значение элемента коллекции, id которого равен заданному",
    "(\\d+)",
    fun(userInput, writer, reader, _, regex) {
        val (idStr) = regex.find(userInput)?.destructured!!
        val id: Int = idStr.toInt()
        val newVehicle = ReaderUtils.readVehicle(writer, reader).copy(id = id)
        repository.updateVehicleById(newVehicle)
        writer.write("Успех!\n")
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}
