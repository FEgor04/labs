package lab6.server.presentation.cli.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase

class SaveCommand(usecase: CommandsHandlerUseCase): CommandImpl("save", "сохраняет коллекцию в файл", "",
    fun (input, writer, _, _, _) {
        try {
            usecase.save()
        }
        catch (e: Exception) {
            writer.write("Не удалось сохранить коллекцию по ошибке $e")
            writer.flush()
            return
        }
        writer.write("Успех!\n")
        writer.flush()
    })