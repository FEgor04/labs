package lab5.cli.commands

import lab5.cli.CLIHandler
import lab5.repositories.VehicleRepository
import java.io.*
import java.util.*

/**
 * Класс команды execute_script
 */
class ExecuteScriptCommand(handler: CLIHandler, writer: BufferedWriter): CommandImpl(
    "execute_script",
    "читать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.",
    ".*",
    fun (userInput, _, _, executeCommandStackTrace: Stack<File>) {
        val regex = Regex("execute_script\\s(.*)")
        val filename: String
        try {
            filename = regex.find(userInput)!!.groups[1]!!.value
        }
        catch (e: Exception) {
            writer.write("Exception: $e\n")
            writer.flush()
            return
        }

        val file = File(filename)
        val stream: InputStreamReader
        try {
            stream = InputStreamReader(file.inputStream())
        }
        catch (e: FileNotFoundException) {
            writer.write("Не существует файла $filename: $e\n")
            writer.flush()
            return
        }
        catch (e: SecurityException) {
            writer.write("Недостаточно прав для доступа к файлу $e\n")
            writer.flush()
            return
        }
        catch(e: Exception) {
            writer.write("Ошибка открытия файла: $e\n")
            writer.flush()
            return
        }

        if(file in executeCommandStackTrace) {
            writer.write("https://youtu.be/dQw4w9WgXcQ\n")
            writer.flush()
            return
        }

        executeCommandStackTrace.push(file)

        val recursionRegex = Regex("execute_script\\s(.*)")
        var lineNumber = 1
        stream.forEachLine {
            handler.handleCommand(it, reader= BufferedReader(stream), writer=writer, executeCommandStackTrace)
            lineNumber += 1
        }
    },
    )