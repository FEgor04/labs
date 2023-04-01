package lab8.lb

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lab8.lb.config.LoadBalancerConfiguration
import lab8.lb.config.PropertiesConfiguration
import lab8.lb.data.balancers.roundrobin.RoundRobinBalancer
import lab8.lb.data.cache.treemap.TreeMapCache
import lab8.lb.data.server.BalancerServer
import lab8.lb.data.synchronizer.postgres.PostgresSynchronizer
import lab8.lb.domain.usecase.BalancerUseCase
import java.io.FileReader
import java.util.*

fun main(): Unit = runBlocking {
    val configReader = FileReader("config/server.properties")
    val properties = Properties()
    properties.load(configReader)
    properties.stringPropertyNames().forEach {
        println(it)
    }
    val config: LoadBalancerConfiguration = PropertiesConfiguration(properties)

    val robin = RoundRobinBalancer(mutableSetOf())
    val hikariDataSource = HikariDataSource(config.hikariConfig)
    val synchronizer = PostgresSynchronizer(hikariDataSource)
    val cache = TreeMapCache()
    val useCase = BalancerUseCase(robin, cache, synchronizer)
    launch {
        while (true) {
            useCase.synchronize()
            delay(config.syncDelay)
        }
    }
    val server = BalancerServer(useCase, config)
    server.run()
}
