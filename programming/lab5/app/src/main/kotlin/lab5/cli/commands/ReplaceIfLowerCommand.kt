package lab5.cli.commands

import lab5.cli.utils.ReaderUtils
import lab5.repositories.ReplaceIfLowerResults
import lab5.repositories.VehicleRepository
import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Класс команды replace_if_lower.
 * Использование: replace_if_lower ID
 * Удаляет все элементы коллекции, чей ID больше, чем данный
 */
class ReplaceIfLowerCommand(repository: VehicleRepository, writer: BufferedWriter, reader: BufferedReader): CommandImpl(
    "replace_if_lower",
    "заменить значение по ключу, если новое значение меньше старого",
    "\\d*",
    fun (userInput, _, _, _) {
        val id: Int
        try {
            val regex = Regex("replace_if_lower\\s(\\d*)")
            val (idStr) = regex.find(userInput)?.destructured!!
            id = idStr.toInt()
        }
        catch (e: Exception) {
            writer.write("Bad input: $e\n")
            writer.flush()
            return
        }
        val element = ReaderUtils.readVehicle(writer, reader)
        val result = repository.replaceIfLower(id, element)
        when (result) {
            ReplaceIfLowerResults.NOT_EXISTS -> writer.write("Такого ключа не существует. Вставлен новый элемент.\n")
            ReplaceIfLowerResults.REPLACED -> writer.write("Успешно заменено.\n")
            ReplaceIfLowerResults.NOT_REPLACED -> writer.write("Элемент по ключу меньше данного. Не заменено.\n")
        }
        writer.flush()
    },
)