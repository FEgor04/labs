package lab8.server.presentation.cli.commands

class ExitCommand(shutdownFunc: () -> Unit) : CommandImpl(
    "exit",
    "выходит из программы",
    "",
    fun(_, writer, _, _, _) {
        shutdownFunc()
        writer.write("Успех!\n")
        writer.flush()
    }
)
