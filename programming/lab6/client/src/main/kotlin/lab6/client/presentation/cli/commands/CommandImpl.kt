package lab6.client.presentation.cli.commands

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.util.*

/**
 * Тип обработчика команды
 * @param userInput ввод пользователя
 * @param writer поток, в который надо осуществлять вывод
 * @param reader поток, из которого надо при необходимости читать данные
 * @param executeCommandStackTrace стэк вызова команды execute_script.
 * @param checker regex-чекер, используемый для проверки команды.
 */
typealias CommandHandler = (
    userInput: String,
    writer: BufferedWriter,
    reader: BufferedReader,
    executeCommandStackTrace: Stack<File>,
    checker: Regex,
) -> Unit

/**
 * Класс команды в CLI
 * @property commandName навзание команды
 * @property description описание команды
 * @property argumentsPattern regex-паттерн аргументов, необходим для проверки в check()
 * @property handler функция, обрабатывающая команду
 * @property regexChecker regex-чекер, проверяющий подходит ли ввод пользователя под данную команду.
 * При реализации рекомендуется использовать группы для последующего вычленения аргументов из ввода пользователя.
 */
abstract class CommandImpl(
    protected val commandName: String,
    protected val description: String,
    private val argumentsPattern: String,
    protected val handler: CommandHandler
) : Command {
    protected open val regexChecker: Regex = when (argumentsPattern.isEmpty()) {
        false -> Regex("^[ \\t]*$commandName[ \\t]+$argumentsPattern[ \t]*$\$")
        true -> Regex("^[ \\t]*$commandName[ \\t]*$\$")
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
        return handler(userInput, writer, reader, executeCommandStackTrace, regexChecker)
    }

    override fun toString(): String {
        return "$commandName - $description"
    }
}