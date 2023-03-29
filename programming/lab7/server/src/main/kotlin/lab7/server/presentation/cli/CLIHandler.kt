package lab7.server.presentation.cli

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import lab7.server.domain.auth.AuthManager
import lab7.server.domain.persistence.PersistenceManager
import lab7.server.domain.usecases.CommandsHandlerUseCase
import lab7.server.presentation.cli.commands.*
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Обработчик CLI команд
 * @param repository репозиторий транспорта
 * @property shouldContinue нужно ли продолжать обрабатывать команду на следующем цикле ввода
 */
class CLIHandler(
    private val useCase: CommandsHandlerUseCase,
    pManager: PersistenceManager,
    authManager: AuthManager,
    shutdownFunc: () -> Unit
) {
    private var commands = mutableListOf<Command>()

    init {
        commands += ExitCommand { shouldContinue = false; shutdownFunc() }
        commands += CreateUserCommand(useCase)
        commands += HelpCommand {
            this.commands
                .map { it.toString() }
                .reduce { acc, s -> "$acc\n$s" } + "\n"
        }
        commands += GenerateRandomCommand(pManager, authManager)
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
    suspend fun start(inputReader: BufferedReader, outputWriter: BufferedWriter) {
        while (shouldContinue) {
            val userInput = withContext(Dispatchers.IO) {
                inputReader.readLine()
            }
            handleCommand(userInput, reader = inputReader, writer = outputWriter)
        }
        delay(readerDelay)
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

    companion object {
        private val readerDelay: Duration = 100.milliseconds
    }
}
