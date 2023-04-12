package lab8.lb.config

import com.zaxxer.hikari.HikariConfig
import lab8.config.UDPConfiguration

interface LoadBalancerConfiguration {
    val udpConfiguration: UDPConfiguration
    val hikariConfig: HikariConfig
    val loadBalancerPort: Int
    val syncDelay: Long
    val workersNumber: Int
    val producersNumber: Int
    val consumersNumber: Int
}
