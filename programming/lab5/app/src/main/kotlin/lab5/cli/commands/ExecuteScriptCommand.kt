package lab5.cli.commands

import lab5.cli.CLIHandler
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.*

/**
 * Класс команды execute_script
 */
class ExecuteScriptCommand(
    handler: CLIHandler,
    inputStreamProvider: (File) -> InputStreamReader = { InputStreamReader(it.inputStream()) }
) : CommandImpl(
    "execute_script",
    "читать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.",
    "([\\w \\t]+)",
    fun(userInput, writer, _, executeCommandStackTrace: Stack<File>, regex) {
        val filename: String
        try {
            filename = regex.find(userInput)!!.groups[1]!!.value
        } catch (e: Exception) {
            writer.write("Exception: $e\n")
            writer.flush()
            return
        }

        val file = File(filename)
        val stream: InputStreamReader
        try {
            stream = inputStreamProvider(file)
        }  catch (e: Exception) {
            when(e) {
                is FileNotFoundException -> {
                    writer.write("Нет файла $file или он недоступен для записи: $e\n")
                }
                is SecurityException -> {
                    writer.write("Недостаточно прав чтобы открыть файл $file: $e\n")
                }
                else -> {
                    writer.write("Не удалось загрузить данные из файла. Ошибка: $e\n")
                }
            }
            writer.flush()
            return
        }

        if (file in executeCommandStackTrace) {
            writer.write("https://youtu.be/dQw4w9WgXcQ\n")
            writer.flush()
            return
        }

        executeCommandStackTrace.push(file)

        handler.handleStream(BufferedReader(stream), writer, executeCommandStackTrace)
    },
)