package lab8.server

import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.metrics.prometheus.PrometheusMetricsTrackerFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lab8.server.config.PropertiesFileConfiguration
import lab8.server.data.auth.PostgresAuthManager
import lab8.server.data.notifier.UDPNotifier
import lab8.server.data.persistence.PostgresPersistenceManager
import lab8.server.data.repositories.VehiclePostgresRepository
import lab8.server.data.udp.UDPServer
import lab8.server.domain.auth.AuthManager
import lab8.server.domain.persistence.PersistenceManager
import lab8.server.domain.repositories.VehicleRepository
import lab8.server.domain.usecases.CommandsHandlerUseCase
import lab8.server.presentation.cli.CLIHandler
import java.io.FileReader
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>): Unit = runBlocking {
    val configReader = FileReader("config/server.properties")
    val properties = Properties()
    properties.load(configReader)
    val config = PropertiesFileConfiguration(properties)

    val hikariConfig = config.databaseConfiguration
    val dataSource = HikariDataSource(hikariConfig)
    dataSource.metricsTrackerFactory = PrometheusMetricsTrackerFactory()

    val persistenceManager: PersistenceManager = PostgresPersistenceManager(dataSource)
    val repository: VehicleRepository = VehiclePostgresRepository(persistenceManager)
    val manager: AuthManager = PostgresAuthManager(dataSource)
    repository.load()
    val useCase = CommandsHandlerUseCase(repository, manager, UDPNotifier(config.syncPort, config.udpConfiguration))

    val nodeNumber = args.getOrNull(0)?.toIntOrNull() ?: 1
    println("Node number: $nodeNumber")
    val udpHandler = UDPServer(
        useCase = useCase,
        configuration = config,
    )

    launch {
        udpHandler.run()
    }

    val cliHandler = CLIHandler(useCase, persistenceManager, manager) { exitProcess(0) }
    launch {
        cliHandler.start(System.`in`.bufferedReader(), System.out.bufferedWriter())
    }
}
