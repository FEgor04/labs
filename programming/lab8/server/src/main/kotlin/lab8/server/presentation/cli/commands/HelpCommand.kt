package lab8.server.presentation.cli.commands

class HelpCommand(f: () -> String) : CommandImpl(
    "help",
    "список команд",
    "",
    fun (_, writer, _, _, _) {
        writer.write(f())
        writer.flush()
    }
)
