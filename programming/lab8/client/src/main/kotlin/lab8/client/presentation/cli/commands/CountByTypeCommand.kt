package lab8.client.presentation.cli.commands

import lab8.client.domain.RemoteCommandHandler
import lab8.entities.vehicle.VehicleType

/**
 * Класс команды remove_key
 */
class CountByTypeCommand(handler: RemoteCommandHandler) : CommandImpl(
    "count_by_type",
    "вывести количество элементов, значение поля type которых равно заданному",
    "(\\w*)",
    fun(userInput, writer, _, _, regex) {
        val type: VehicleType?
        val (typeStr) = regex.find(userInput)?.destructured!!
        type = if (typeStr.isEmpty()) {
            null
        } else {
            VehicleType.valueOf(typeStr.trim().uppercase())
        }

        val count = handler.countByType(type)
        writer.write("В коллекции $count элементов с типом $type\n")
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} type - ${this.description}"
    }

    override val regexChecker = Regex("^[ \\t]*count_by_type[ \\t]*(\\w*)[ \\t]*$")
}
