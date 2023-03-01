package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler
import lab6.shared.entities.vehicle.Vehicle

/**
 * Класс команды remove_key
 */
class GenerateRandomCommand(handler: RemoteCommandHandler) : CommandImpl(
    "generate_random",
    "генерирует n рандомных транспортных средств и добавляет в коллекцию",
    "(\\d+)",
    fun(userInput, writer, _, _, regex) {
        val n: Int
        try {
            val (idStr) = regex.find(userInput)?.destructured!!
            n = idStr.toInt()
        } catch (e: Exception) {
            writer.write("Неправильный ввод.\n")
            writer.flush()
            return
        }
        try {
            repeat(n) {
                handler.add(Vehicle.generateRandomVehicle())
            }
            writer.write("Успех!\n")
        }
        catch(e: Exception) {
            writer.write("Ошибка $e\n")
        }
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} n - ${this.description}"
    }
}