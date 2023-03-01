package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler
import lab6.shared.entities.vehicle.VehicleType

/**
 * Класс команды remove_key
 */
class CountByTypeCommand(handler: RemoteCommandHandler) : CommandImpl(
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

        try {
            val count = handler.countByType(type)
            writer.write("В коллекции $count элементов с типом $type\n")
        }
        catch(e: Exception) {
            writer.write("Ошибка $e\n")
        }
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} type - ${this.description}"
    }

    override val regexChecker = Regex("^[ \\t]*count_by_type[ \\t]*(\\w*)[ \\t]*$")
}