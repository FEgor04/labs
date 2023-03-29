package lab7.client

import lab7.client.data.UDPBetterChannelService
import lab7.client.exceptions.ClientException
import lab7.client.presentation.cli.CLIHandler
import lab7.config.SharedConfig
import lab7.entities.user.User
import java.io.Console
import java.net.InetSocketAddress

fun main(args: Array<String>) {
    val console = System.console()
    val user = getLoginFromEnv() ?: login(console)
//    val port = args.getOrNull(0)?.toIntOrNull() ?: SharedConfig.SERVER_PORT

    val handler = UDPBetterChannelService(
        user,
        InetSocketAddress("localhost", SharedConfig.LOAD_BALANCER_PORT)
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
