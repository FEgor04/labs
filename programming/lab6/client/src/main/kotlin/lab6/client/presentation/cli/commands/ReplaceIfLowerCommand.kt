package lab6.client.presentation.cli.commands

import lab6.client.presentation.cli.utils.ReaderUtils
import lab6.client.domain.RemoteCommandHandler
import lab6.shared.entities.dtos.responses.ReplaceIfLowerResults

/**
 * Класс команды replace_if_lower.
 * Использование: replace_if_lower ID
 * Удаляет все элементы коллекции, чей ID больше, чем данный
 */
class ReplaceIfLowerCommand(repository: RemoteCommandHandler) : CommandImpl(
    "replace_if_lower",
    "заменить значение по ключу, если новое значение меньше старого",
    "(\\d+)",
    fun(userInput, writer, reader, _, regex) {
        val id: Int
        try {
            val (idStr) = regex.find(userInput)?.destructured!!
            id = idStr.toInt()
        } catch (e: Exception) {
            writer.write("Некорректный ввод.\n")
            writer.flush()
            return
        }
        try {
            val element = ReaderUtils.readVehicle(writer, reader)
            when (repository.replaceIfLower(id, element)) {
                ReplaceIfLowerResults.NOT_EXISTS -> writer.write("Такого ключа не существует. Вставлен новый элемент.\n")
                ReplaceIfLowerResults.REPLACED -> writer.write("Успешно заменено.\n")
                ReplaceIfLowerResults.NOT_REPLACED -> writer.write("Элемент по ключу меньше данного. Не заменено.\n")
            }
        }
        catch(e: Exception) {
            writer.write("Ошибка: $e\n")
        }
        writer.flush()
    },
)