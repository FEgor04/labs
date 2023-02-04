package lab5.cli.commands

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.util.*

/**
 * Интерфейс команды
 */
interface Command {
    /**
     * Проверяет, вызывает ли пользователь данную команду
     */
    fun check(userInput: String): Boolean

    /**
     * Обрабатывает ввод пользователя
     * @param userInput ввод пользователя
     * @param writer BufferedWriter, в который надо выводить текст
     * @param reader BufferedReader, из которого надо читать текст.
     * Необходим для выполнения команды execute_script для исполнения команд из файла
     * @param executeCommandStackTrace стэк-трэйс команд execute_script.
     * Необходим для предовтращеняи рекурсии.
     */
    fun handle(userInput: String, writer: BufferedWriter, reader: BufferedReader, executeCommandStackTrace: Stack<File> = Stack())
}