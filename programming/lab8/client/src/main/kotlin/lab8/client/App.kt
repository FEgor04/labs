package lab8.client

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import lab8.client.config.ClientConfiguration
import lab8.client.config.PropertiesConfiguration
import lab8.client.data.UDPSynchronizer
import lab8.client.data.UDPBetterChannelService
import lab8.client.domain.RemoteCommandHandler
import lab8.client.domain.RemoteSynchronizer
import lab8.client.presentation.compose.ui.i18n.InternationalizationProvider
import lab8.client.presentation.compose.ui.i18n.properties.PropertiesInternationalizationProvider
import lab8.client.presentation.compose.ui.login.LoginScreen
import lab8.entities.user.User
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import java.io.Console
import java.io.File
import java.io.FileReader
import java.util.Properties

val appModules = module {
    singleOf(::PropertiesInternationalizationProvider) {
        bind<InternationalizationProvider>()
        createdAtStart()
    }
    single<PropertiesConfiguration> {
        val properties = Properties()
        val reader = FileReader(File("config/server.properties"))
        properties.load(reader)
        PropertiesConfiguration(properties)
    } withOptions {
        bind<ClientConfiguration>()
    }
    factory<User> { User.generateRandomUser() }
    singleOf(::UDPBetterChannelService) {
        createdAtStart()
        bind<RemoteCommandHandler>()
    }
    singleOf(::UDPSynchronizer) {
        createdAtStart()
        bind<RemoteSynchronizer>()
    }
}

fun main(args: Array<String>) = application {
    startKoin {
        modules(appModules)
    }
    val provider by remember {
        mutableStateOf(
            inject<InternationalizationProvider>(InternationalizationProvider::class.java).value
        )
    }
    var localization by mutableStateOf(provider.chosen)
    var currentUser: User? by remember { mutableStateOf(null as User?) }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Lab #8",
        state = rememberWindowState(width = 1500.dp, height = 500.dp)
    ) {
        MaterialTheme {
            Navigator(
                LoginScreen(
                    provider,
                    currentUser
                ) { currentUser = it }
            ) { navigator ->
                CurrentScreen()
            }
        }
    }
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
