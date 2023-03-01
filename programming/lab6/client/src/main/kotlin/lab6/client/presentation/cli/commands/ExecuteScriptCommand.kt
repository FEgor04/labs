package lab6.client.presentation.cli.commands

import lab6.client.domain.RemoteCommandHandler
import lab6.client.exceptions.ClientException
import lab6.client.presentation.cli.CLIHandler
import java.io.File

/**
 * Класс команды execute_script
 * TODO: переделать под чистую архитектуру - чтение файла и бизнес-логика должны располагаться на уровне data,
 * TODO: а не на уровне presentation
 *
 */
class ExecuteScriptCommand(handler: CLIHandler) : CommandImpl(
    "execute_script",
    "считать файл и запустить его как скрипт",
    "(.+)",
    fun(userInput, writer, _, stack, regex) {
        val filename: String
        try {
            filename = regex.find(userInput)?.destructured!!.component1()
        } catch (e: Exception) {
            writer.write("Неправильный ввод.\n")
            writer.flush()
            return
        }
        val file = File(filename)
        if(file in stack) {
            throw ClientException.ExecuteScriptRecursion(file.name)
        }
        stack.push(file)
        val fileInputStream = file.inputStream().bufferedReader()
        try {
            handler.handleStream(fileInputStream, writer, stack)
            writer.write("Успех!\n")
            writer.flush()
        }
        catch (e: ClientException.ExecuteScriptRecursion) {
            writer.write("Рекурсия в скрипте ${e.fileName}\n")
            writer.flush()
        }
    },
) {
    override fun toString(): String {
        return "${this.commandName} filename - ${this.description}"
    }
}