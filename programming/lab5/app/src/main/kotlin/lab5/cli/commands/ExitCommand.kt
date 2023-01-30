package lab5.cli.commands

import java.io.BufferedWriter
import kotlin.system.exitProcess

/**
 * Класс команды exit
 */
class ExitCommand(writer: BufferedWriter, stopHandling: () -> Unit = { exitProcess(0) }): CommandImpl(
    "exit",
    "завершить программу (без сохранения в файл)",
    "",
    fun (_, _, _, _) {
        writer.write("Goodbye Blue Sky\n")
        writer.flush()
        stopHandling()
    },
    )