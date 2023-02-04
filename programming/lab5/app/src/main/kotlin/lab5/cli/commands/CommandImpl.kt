package lab5.cli.commands

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.util.*

/**
 * Тип обработчика команды
 */
typealias CommandHandler = (userInput: String, writer: BufferedWriter, reader: BufferedReader, executeCommandStackTrace: Stack<File>) -> Unit

/**
 * Класс команды в CLI
 * @property commandName навзание команды
 * @property description описание команды
 * @property argumentsPattern regex-паттерн аргументов, необходим для проверки в check()
 * @property handler - функция, обрабатывающая команду
 */
open class CommandImpl(
    protected val commandName: String,
    protected val description: String,
    private val argumentsPattern: String,
    protected val handler: CommandHandler
) : Command {
    private val regexChecker: Regex = when (argumentsPattern.isEmpty()) {
        false -> Regex("[ \t]*$commandName[ \t]+$argumentsPattern[ \t]*$")
        true -> Regex("[ \t]*$commandName[ \t]*$")
    }

    /**
     * Проверяет, вызывает ли пользователь данную команду
     */
    override fun check(userInput: String): Boolean {
        return regexChecker.matches(userInput)
    }

    /**
     * Обрабатывает ввод пользователя
     * @param userInput ввод пользователя
     * @param writer BufferedWriter, в который надо выводить текст
     * @param reader BufferedReader, из которого надо читать текст.
     * Необходим для выполнения команды execute_script для исполнения команд из файла
     * @param executeCommandStackTrace стэк-трэйс команд execute_script.
     * Необходим для предовтращеняи рекурсии.
     */
    override fun handle(
        userInput: String,
        writer: BufferedWriter,
        reader: BufferedReader,
        executeCommandStackTrace: Stack<File>
    ) {
        return handler(userInput, writer, reader, executeCommandStackTrace)
    }

    override fun toString(): String {
        return "$commandName - $description"
    }
}