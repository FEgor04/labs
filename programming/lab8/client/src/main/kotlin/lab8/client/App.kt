package lab8.client

import androidx.compose.ui.window.application
import lab8.client.config.ClientConfiguration
import lab8.client.config.PropertiesConfiguration
import lab8.client.data.UDPBetterChannelService
import lab8.client.exceptions.ClientException
import lab8.client.presentation.cli.CLIHandler
import lab8.entities.user.User
import java.io.Console
import java.io.FileReader
import java.util.*

fun main(args: Array<String>) = application {
    val console = System.console()
    val configReader = FileReader("config/server.properties")
    val properties = Properties()
    properties.load(configReader)
    val config: ClientConfiguration = PropertiesConfiguration(properties)
    val user = getLoginFromEnv() ?: login(console)

    val handler = UDPBetterChannelService(
        user,
        config,
    )
    while (handler.user.id <= 0) {
        try {
            handler.user = handler.tryToAuth()
        } catch (e: ClientException.AuthException) {
            println("Неудачный вход: ${e.message}. Попробуйте еще.")
            handler.user = login(console)
        }
    }
    val cliHandler = CLIHandler(console, handler)
    cliHandler.start()
}

fun getLoginFromEnv(): User? {
    val login = System.getenv("LOGIN")
    val password = System.getenv("PASSWORD")
    if (login.isNullOrEmpty() || password.isNullOrEmpty()) {
        return null
    }
    println("Логин и пароль загружены из переменных окружения")
    return User(name = login, password = password, id = -1)
}

fun login(console: Console): User {
    console.printf("Введите логин:\n")
    val login = console.readLine().trim()
    console.printf("Введите пароль:\n")
    val password = String(console.readPassword())
    return User(login, password, -1)
}
