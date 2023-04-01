package lab8.client.presentation.cli.commands

import lab8.client.domain.RemoteCommandHandler

/**
 * Класс команды remove_key
 */
class CountLessThanEnginePowerCommand(repository: RemoteCommandHandler) : CommandImpl(
    "count_less_than_engine_power",
    "вывести количество элементов, значение поля enginePower которых меньше заданного",
    "([-|+]?[\\d]+.?[\\d]*)",
    fun(userInput, writer, _, _, regex) {
        val (enginePowerStr) = regex.find(userInput)?.destructured!!
        val enginePower: Double = enginePowerStr.toDouble()
        val count = repository.countLessThanEnginePower(enginePower)
        writer.write("В коллекции содержиться $count элементов с мощностью двигателя меньше чем $enginePower\n")
        writer.flush()
    },
) {
    override fun toString(): String {
        return "${this.commandName} id - ${this.description}"
    }
}
