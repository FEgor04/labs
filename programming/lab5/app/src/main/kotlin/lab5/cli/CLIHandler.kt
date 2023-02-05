package lab5.cli

import lab5.cli.commands.*
import lab5.repositories.VehicleRepository
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.util.*

/**
 * Обработчик CLI команд
 * @param repository репозиторий транспорта
 * @property shouldContinue нужно ли продолжать обрабатывать команду на следующем цикле ввода
 */
class CLIHandler(private val repository: VehicleRepository) {
    private var commands = mutableListOf<Command>()

    init {
        commands += HelpCommand(this.commands)
        commands += InfoCommand(this.repository)
        commands += InsertCommand(this.repository)
        commands += ShowCommand(this.repository)
        commands += ExitCommand { this.shouldContinue = false }
        commands += SaveCommand(repository)
        commands += ClearCommand(repository)
        commands += RemoveKeyCommand(repository)
        commands += UpdateCommand(repository)
        commands += RemoveGreaterCommand(repository)
        commands += RemoveLowerCommand(repository)
        commands += ReplaceIfLowerCommand(repository)
        commands += MinByIdCommand(repository)
        commands += CountByTypeCommand(repository)
        commands += CountLessThanEnginePowerCommand(repository)
        commands += ExecuteScriptCommand(this)
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
     * Блокирует поток, перестает при изменении поля shouldContinue на false
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