package lab5.cli.commands

import java.io.BufferedWriter

/**
 * Класс команды help
 */
class HelpCommand(commandsList: List<Command>): CommandImpl(
    "help",
    "вывести справку по доступным командам",
    "",
    fun (_, writer, _, _) {
        commandsList.forEach { writer.write("- $it\n") }
        writer.flush()
    },
    )