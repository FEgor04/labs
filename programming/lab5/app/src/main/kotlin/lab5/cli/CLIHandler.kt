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
 * @param inputReader reader с которорго осуществляется ввод
 * @param outputWriter writer на который осуществляется вывод
 */
class CLIHandler(private val repository: VehicleRepository) {
    private var commands = mutableListOf<Command>()

    init {
        commands += HelpCommand(this.commands)
        commands += InfoCommand(this.repository)
        commands += InsertCommand(this.repository)
        commands += ShowCommand(this.repository)
        commands += ExitCommand() { this.shouldContinue = false }
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
    fun handleCommand(
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
     * Блокирует поток, перестает только введенной командой exit
     */
    fun start(inputReader: BufferedReader, outputWriter: BufferedWriter) {
        while (shouldContinue) {
            val userInput = inputReader.readLine()
            handleCommand(userInput, reader = inputReader, writer = outputWriter)
        }
    }

    fun handleStream(input: BufferedReader, output: BufferedWriter, stack: Stack<File>) {
        while (input.ready()) {
            val userInput = input.readLine()
            handleCommand(userInput, writer = output, reader = input, executeCommandStackTrace = stack)
        }
    }

    private var shouldContinue = true
}