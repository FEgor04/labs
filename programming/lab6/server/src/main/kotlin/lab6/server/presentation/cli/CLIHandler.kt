package lab6.server.presentation.cli

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.server.presentation.cli.commands.Command
import lab6.server.presentation.cli.commands.ExitCommand
import lab6.server.presentation.cli.commands.SaveCommand
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.util.*

/**
 * Обработчик CLI команд
 * @param repository репозиторий транспорта
 * @property shouldContinue нужно ли продолжать обрабатывать команду на следующем цикле ввода
 */
class CLIHandler(private val useCase: CommandsHandlerUseCase, shutdownFunc: () -> Unit) {
    private var commands = mutableListOf<Command>()

    init {
        commands += SaveCommand(useCase)
        commands += ExitCommand { shouldContinue = false; shutdownFunc() }
    }

    /**
     * Обрабатывает команду пользователя
     * Весь выход записывается в OutputStream
     * @param executeCommandStackTrace стэк вызовов команды execute_script.
     * Необходим для предовтращения рекурсивных вызовов
     */
    private fun handleCommand(
        userInput: String,
        reader: BufferedReader,
        writer: BufferedWriter,
        executeCommandStackTrace: Stack<File> = Stack()
    ) {
        var goodInput = false
        val command = commands.find { it.check(userInput) }
        if (command == null) {
            writer.write("Нет такой команды =(\n")
            writer.flush()
        } else {
            command.handle(
                userInput,
                reader = reader,
                writer = writer,
                executeCommandStackTrace = executeCommandStackTrace
            )
        }
    }

    /**
     * Начинает обработку команд.
     * Блокирует поток, снимает блокировку при изменении поля shouldContinue на false
     */
    fun start(inputReader: BufferedReader, outputWriter: BufferedWriter) {
        while (shouldContinue) {
            val userInput = inputReader.readLine()
            handleCommand(userInput, reader = inputReader, writer = outputWriter)
        }
    }

    /**
     * Обрабатывает команды из потока.
     * Будет продолжать обрабатывать поток пока он ready.
     * Используется для ввода команд из файла.
     * @param input входной поток (например файл, stdin)
     * @param output выходной поток (например файл, stdout)
     * @param stack стэк вызова функции execute_script. Необходим для преодотвращения рекурсии
     */
    fun handleStream(input: BufferedReader, output: BufferedWriter, stack: Stack<File>) {
        while (input.ready()) {
            val userInput = input.readLine()
            handleCommand(userInput, writer = output, reader = input, executeCommandStackTrace = stack)
        }
    }

    private var shouldContinue = true
}