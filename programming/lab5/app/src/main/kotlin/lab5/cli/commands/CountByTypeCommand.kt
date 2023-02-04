package lab5.cli.commands

import lab5.entities.vehicle.VehicleType
import lab5.repositories.VehicleRepository
import java.io.BufferedWriter

/**
 * Класс команды remove_key
 */
class CountByTypeCommand(repository: VehicleRepository): CommandImpl(
    "count_by_type",
    "вывести количество элементов, значение поля type которых равно заданному",
    "\\w",
    fun (userInput, writer, _, _) {
        val type: VehicleType?
        try {
            val regex = Regex("count_by_type\\s*(\\w*)")
            val (typeStr) = regex.find(userInput)?.destructured!!
            if(typeStr.isEmpty()) {
                type = null
            }
            else {
                type = VehicleType.valueOf(typeStr.trim().uppercase())
            }
        }
        catch (e: Exception) {
            writer.write("Некорректный ввод. Допустимые значения type: ${VehicleType.values().map { it.toString() }} или пустая строка для null\n")
            writer.flush()
            return
        }

        val count = repository.countByType(type)
        writer.write("В коллекции содержиться $count элементов с типом $type.\n")
        writer.flush()
    },
    ) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}