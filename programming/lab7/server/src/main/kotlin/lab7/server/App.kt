package lab7.server

import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.metrics.prometheus.PrometheusMetricsTrackerFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lab7.server.config.ConfigurationProvider
import lab7.server.config.HardcodedServerConfiguration
import lab7.server.data.auth.PostgresAuthManager
import lab7.server.data.persistence.PostgresPersistenceManager
import lab7.server.data.repositories.VehiclePostgresRepository
import lab7.server.data.udp.UDPServer
import lab7.server.domain.auth.AuthManager
import lab7.server.domain.persistence.PersistenceManager
import lab7.server.domain.repositories.VehicleRepository
import lab7.server.domain.usecases.CommandsHandlerUseCase
import lab7.server.presentation.cli.CLIHandler
import kotlin.system.exitProcess

fun main(args: Array<String>): Unit = runBlocking {
    ConfigurationProvider.setConfiguration(HardcodedServerConfiguration())
    val config = ConfigurationProvider.getConfiguration()

//    DefaultExports.initialize()
//    HTTPServer.Builder()
//        .withPort(config.prometheusPort)
//        .build()

    val hikariConfig = config.databaseConfiguration
    val dataSource = HikariDataSource(hikariConfig)
    dataSource.metricsTrackerFactory = PrometheusMetricsTrackerFactory()

    val persistenceManager: PersistenceManager = PostgresPersistenceManager(dataSource)
    val repository: VehicleRepository = VehiclePostgresRepository(persistenceManager)
    val manager: AuthManager = PostgresAuthManager(dataSource)
    repository.load()
    val useCase = CommandsHandlerUseCase(repository, manager)

    /*
    val synchronizer = launch {
        while (true) {
            repository.load()
            delay(SYNC_DELAY)
        }
    }
     */

    val nodeNumber = args.getOrNull(0)?.toIntOrNull() ?: 1
    println("Node number: $nodeNumber")
    val udpHandler = UDPServer(
        useCase = useCase,
        port = lab7.config.SharedConfig.SERVER_PORT + nodeNumber - 1,
        config = config.udpConfiguration,
        loadBalancerAddr = config.loadBalancerAddress
    )

    launch {
        udpHandler.run()
    }

    val cliHandler = CLIHandler(useCase, persistenceManager, manager) { exitProcess(0) }
    launch {
        cliHandler.start(System.`in`.bufferedReader(), System.out.bufferedWriter())
    }
}
