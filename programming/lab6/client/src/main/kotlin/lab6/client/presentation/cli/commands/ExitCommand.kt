package lab6.client.presentation.cli.commands

import kotlin.system.exitProcess

/**
 * Класс команды exit
 */
class ExitCommand(stopHandling: () -> Unit = { exitProcess(0) }) : CommandImpl(
    "exit",
    "завершить программу (без сохранения в файл)",
    "",
    fun(_, writer, _, _, _) {
        writer.write("Goodbye Blue Sky\n")
        writer.flush()
        stopHandling()
    },
)