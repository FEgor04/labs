package data.server

import config.SynchronizerConfiguration
import domain.Synchronizer
import domain.UseCase
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import lab8.entities.synchronizer.SynchronizerRequest
import lab8.entities.synchronizer.SynchronizerResponse
import lab8.logger.KCoolLogger
import lab8.udp.communicator.UDPCommunicator
import lab8.udp.server.Server
import java.net.InetSocketAddress

class SyncServer(
    private val useCase: UseCase,
    private val communicator: UDPCommunicator,
    private val configuration: SynchronizerConfiguration
) {
    private val handler = SynchronizerRequestsHandler(useCase)
    private val logger by KCoolLogger()

    private val server = Server<SynchronizerRequest, SynchronizerResponse>(
        communicator = communicator,
        config = configuration.udpConfiguration,
        consumersNumber = configuration.producersNumber,
        producersNumber = configuration.producersNumber,
        workesrNumber = configuration.workersNumber,
        handler = handler,
        serializer = { Json.decodeFromString(it) },
        deserializer = { Json.encodeToString(it) },
    )

    suspend fun run() {
        logger.info("Started synchronizer!")
        server.run()
    }
}