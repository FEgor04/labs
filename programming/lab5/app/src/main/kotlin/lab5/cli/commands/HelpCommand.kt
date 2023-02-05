package lab5.cli.commands

/**
 * Класс команды help
 */
class HelpCommand(commandsList: List<Command>) : CommandImpl(
    "help",
    "вывести справку по доступным командам",
    "",
    fun(_, writer, _, _, _) {
        commandsList.forEach { writer.write("- $it\n") }
        writer.flush()
    },
)