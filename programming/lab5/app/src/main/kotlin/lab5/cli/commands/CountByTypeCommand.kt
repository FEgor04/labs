package lab5.cli.commands

import lab5.entities.vehicle.VehicleType
import lab5.repositories.VehicleRepository

/**
 * Класс команды remove_key
 */
class CountByTypeCommand(repository: VehicleRepository) : CommandImpl(
    "count_by_type",
    "вывести количество элементов, значение поля type которых равно заданному",
    "(\\w*)",
    fun(userInput, writer, _, _, regex) {
        val type: VehicleType?
        try {
            val (typeStr) = regex.find(userInput)?.destructured!!
            type = if (typeStr.isEmpty()) {
                null
            } else {
                VehicleType.valueOf(typeStr.trim().uppercase())
            }
        } catch (e: Exception) {
            writer.write(
                "Некорректный ввод. Допустимые значения type: ${
                    VehicleType.values().map { it.toString() }
                } или пустая строка для null\n"
            )
            writer.flush()
            return
        }

        val count = repository.countByType(type)
        writer.write("В коллекции $count элементов с типом $type.\n")
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} type - ${this.description}"
    }

    override val regexChecker = Regex("^[ \\t]*count_by_type[ \\t]*(\\w*)[ \\t]*$")
}