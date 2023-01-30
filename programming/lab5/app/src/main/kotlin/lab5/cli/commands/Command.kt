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
     */
    fun handle(userInput: String, writer: BufferedWriter, reader: BufferedReader, executeCommandStackTrace: Stack<File> = Stack())
}