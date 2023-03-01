package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler


/**
 * Класс команды remove_key
 */
class CountLessThanEnginePowerCommand(repository: RemoteCommandHandler) : CommandImpl(
    "count_less_than_engine_power",
    "вывести количество элементов, значение поля enginePower которых меньше заданного",
    "([-|+]?[\\d]+.?[\\d]*)",
    fun(userInput, writer, _, _, regex) {
        val enginePower: Double
        try {
            val (enginePowerStr) = regex.find(userInput)?.destructured!!
            enginePower = enginePowerStr.toDouble()

        } catch (e: Exception) {
            writer.write("Некорректный ввод.\n")
            writer.flush()
            return
        }

        val count = repository.countLessThanEnginePower(enginePower)
        writer.write("В коллекции содержиться $count элементов с мощностью двигателя меньше чем $enginePower\n")
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}