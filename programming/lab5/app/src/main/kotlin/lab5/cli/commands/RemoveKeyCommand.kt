package lab5.cli.commands

import lab5.repositories.VehicleRepository

/**
 * Класс команды remove_key
 */
class RemoveKeyCommand(repository: VehicleRepository) : CommandImpl(
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
        repository.removeVehicle(id)
        writer.write("Успех!\n")
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}