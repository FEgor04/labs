package lab5.cli.commands

import java.io.BufferedWriter

/**
 * Класс команды help
 */
class HelpCommand(commandsList: List<Command>, writer: BufferedWriter): CommandImpl(
    "help",
    "вывести справку по доступным командам",
    "",
    fun (_, _, _, _) {
        for(command in commandsList) {
            writer.write("- $command\n")
        }
        writer.flush()
    },
    )