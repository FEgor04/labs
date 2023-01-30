package lab5.cli.commands

import lab5.repositories.VehicleRepository
import java.io.BufferedWriter

/**
 * Класс команды remove_key
 */
class RemoveKeyCommand(repository: VehicleRepository, writer: BufferedWriter): CommandImpl(
    "remove_key",
    "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)",
    "\\d*",
    fun (userInput, _, _, _) {
        val id: Int;
        try {
            val regex = Regex("remove_key\\s(\\d*)")
            val (idStr) = regex.find(userInput)?.destructured!!
            id = idStr.toInt()
        } catch(e : Exception) {
            writer.write("Bad input: $e\n")
            writer.flush()
            return
        }
        repository.removeVehicle(id)
        writer.write("Успех!\n")
        writer.flush()
    },
    ) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}