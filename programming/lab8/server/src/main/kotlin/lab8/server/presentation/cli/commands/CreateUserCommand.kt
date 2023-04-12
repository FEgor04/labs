package lab8.server.presentation.cli.commands

import kotlinx.coroutines.runBlocking
import lab8.server.domain.usecases.CommandsHandlerUseCase
import java.sql.SQLException

class CreateUserCommand(usecase: CommandsHandlerUseCase) : CommandImpl(
    "create",
    "создает нового пользователя",
    "(\\w+) (\\w+)",
    fun (input, writer, _, _, checker) {
        val (login, password) = checker.find(input)!!.destructured
        try {
            val id = runBlocking { usecase.createUser(login, password) }
            writer.write("Новый пользователь создан с ID = $id\n")
        } catch (e: SQLException) {
            writer.write("Ошибка: $e\n")
        }
        writer.flush()
    }
)
