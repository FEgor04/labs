package lab5.cli.commands

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.util.*

typealias CommandHandler = (userInput: String, writer: BufferedWriter, reader: BufferedReader, executeCommandStackTrace: Stack<File>) -> Unit

/**
 * Класс команды в CLI
 * @property commandName навзание команды
 * @property description описание команды
 * @property argumentsPattern regex-паттерн аргументов, необходим для проверки в check()
 * @property handler - функция, обрабатывающая команду
 */
open class CommandImpl(protected val commandName: String, protected val description: String, protected val argumentsPattern: String, protected val handler: CommandHandler): Command {
    private val regexChecker: Regex = when(argumentsPattern.isEmpty()) {
        false -> Regex("^$commandName $argumentsPattern")
        true -> Regex("^$commandName")
    }

    /**
     * Проверяет, вызывает ли пользователь данную команду
     */
    override fun check(userInput: String): Boolean {
        return regexChecker.matches(userInput)
    }

    /**
     * Обрабатывает команду
     */
    override fun handle(userInput: String, writer: BufferedWriter, reader: BufferedReader, executeCommandStackTrace: Stack<File>) {
        return handler(userInput, writer, reader, executeCommandStackTrace)
    }

    override fun toString(): String {
        return "$commandName - $description"
    }
}