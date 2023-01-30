package lab5.cli

import lab5.cli.commands.*
import lab5.repositories.ReplaceIfLowerResults
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
class CLIHandler(private val repository: VehicleRepository, private val inputReader: BufferedReader, private val outputWriter: BufferedWriter) {
    private var commands = mutableListOf<Command>()
    init {
        commands += HelpCommand(this.commands, outputWriter)
        commands += InfoCommand(this.repository, outputWriter)
        commands += InsertCommand(this.repository, outputWriter, inputReader)
        commands += ShowCommand(this.repository, outputWriter)
        commands += ExitCommand(outputWriter) { this.shouldContinue = false }
        commands += SaveCommand(repository, outputWriter)
        commands += ClearCommand(repository, outputWriter)
        commands += RemoveKeyCommand(repository, outputWriter)
        commands += UpdateCommand(repository, outputWriter, inputReader)
        commands += RemoveGreaterCommand(repository, outputWriter, inputReader)
        commands += RemoveLowerCommand(repository, outputWriter, inputReader)
        commands += ReplaceIfLowerCommand(repository, outputWriter, inputReader)
        commands += MinByIdCommand(repository, outputWriter)
        commands += CountByTypeCommand(repository, outputWriter)
        commands += CountLessThanEnginePowerCommand(repository, outputWriter)
        commands += ExecuteScriptCommand(this, outputWriter)
    }

    /**
     * Обрабатывает команду пользователя
     * Весь выход записывается в OutputStream
     * @param executeCommandStackTrace стэк вызовов команды execute_script.
     * Необходим для предовтращения рекурсивных вызовов
     */
    fun handleCommand(userInput: String, reader: BufferedReader = this.inputReader, writer: BufferedWriter = this.outputWriter, executeCommandStackTrace: Stack<File> = Stack()) {
        var goodInput = false
        for(command in commands) {
            if(command.check(userInput)) {
                command.handle(userInput, reader=reader, writer=writer, executeCommandStackTrace = executeCommandStackTrace)
                goodInput = true
                break
            }
        }
        if( !goodInput) {
            writer.write("Нет такой команды =(\n")
            writer.flush()
        }
    }

    /**
     * Начинает обработку команд.
     * Блокирует поток, перестает только введенной командой exit
     */
    fun start() {
        while(shouldContinue) {
            val userInput = inputReader.readLine()
            handleCommand(userInput)
        }
    }

    private var shouldContinue = true
}