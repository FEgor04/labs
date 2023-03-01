package lab6.server.presentation.cli.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase

class ExitCommand(shutdownFunc: () -> Unit): CommandImpl("exit", "выходит из программы", "",
    fun (input, writer, _, _, _) {
        try {
            shutdownFunc()
        }
        catch (e: Exception) {
            writer.write("Не удалось сохранить коллекцию по ошибке $e")
            writer.flush()
            return
        }
        writer.write("Успех!\n")
        writer.flush()
    })