package config

import lab8.config.UDPConfiguration

interface SynchronizerConfiguration {
    val synchronizerPort: Int
    val udpConfiguration: UDPConfiguration
    val workersNumber: Int
    val producersNumber: Int
    val consumersNumber: Int
}
