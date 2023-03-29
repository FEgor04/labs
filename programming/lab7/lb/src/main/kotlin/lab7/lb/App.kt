package lab7.lb

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lab7.config.SharedConfig
import lab7.lb.data.balancers.roundrobin.RoundRobinBalancer
import lab7.lb.data.cache.treemap.TreeMapCache
import lab7.lb.data.server.UDPServer
import lab7.lb.data.synchronizer.postgres.PostgresSynchronizer
import lab7.lb.domain.usecase.BalancerUseCase

fun main(): Unit = runBlocking {
    val robin = RoundRobinBalancer(mutableSetOf())
    val hikariConfig = HikariConfig("hikari.properties")
    val hikariDataSource = HikariDataSource(hikariConfig)
    val synchronizer = PostgresSynchronizer(hikariDataSource)
    val cache = TreeMapCache()
    val useCase = BalancerUseCase(robin, cache, synchronizer)
    launch {
        while(true) {
            useCase.synchronize()
            delay(1000)
        }
    }
    val server = UDPServer(useCase, SharedConfig.LOAD_BALANCER_PORT)
    server.run()
}
