package lab6.client.presentation.cli.commands

import com.sun.imageio.plugins.common.ReaderUtil
import lab6.client.domain.RemoteCommandHandler
import lab6.client.presentation.cli.utils.ReaderUtils

/**
 * Класс команды update
 */
class UpdateCommand(repository: RemoteCommandHandler) : CommandImpl(
    "update",
    "обновить значение элемента коллекции, id которого равен заданному",
    "(\\d+)",
    fun(userInput, writer, reader, _, regex) {
        val id: Int
        try {
            val (idStr) = regex.find(userInput)?.destructured!!
            id = idStr.toInt()
        } catch (e: Exception) {
            writer.write("Неправильный ввод. Попробуйте еще.\n")
            writer.flush()
            return
        }

        val newVehicle = ReaderUtils.readVehicle(writer, reader).copy(id=id)
        repository.updateVehicleById(newVehicle)
        writer.write("Успех!\n")
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}
