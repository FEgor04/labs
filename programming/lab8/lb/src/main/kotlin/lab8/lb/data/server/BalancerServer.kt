package lab8.lb.data.server

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.ResponseDTO
import lab8.lb.config.LoadBalancerConfiguration
import lab8.lb.domain.usecase.BalancerUseCase
import lab8.logger.KCoolLogger
import lab8.udp.acknowledge.FlowAcknowledgeProvider
import lab8.udp.communicator.BetterDatagramSocket
import lab8.udp.server.Server
import java.net.DatagramSocket
import java.net.InetSocketAddress

class BalancerServer(
    private val useCase: BalancerUseCase,
    private val configuration: LoadBalancerConfiguration,
) {
    private val logger by KCoolLogger()
    private val stupidSocket = DatagramSocket(configuration.loadBalancerPort)
    private val flowAcknowledgeProvider = FlowAcknowledgeProvider.generate()
    private val betterDatagramSocket = BetterDatagramSocket(
        stupidSocket,
        configuration.udpConfiguration.ackBytes,
        configuration.udpConfiguration.chunkSize,
        configuration.udpConfiguration.isFinalChunkByte,
        flowAcknowledgeProvider,
    )

    private val executor = UDPBalancer(useCase, configuration)

    private val server = Server<RequestDTO, ResponseDTO>(
        betterDatagramSocket,
        config = configuration.udpConfiguration,
        configuration.consumersNumber,
        configuration.producersNumber,
        configuration.workersNumber,
        executor,
        { Json.decodeFromString(it) },
        { Json.encodeToString(it) },
    )

    suspend fun run() {
        val serverAddress = stupidSocket.localSocketAddress as InetSocketAddress
        logger.info("Started load balancer on addr: $serverAddress")
        server.run()
    }
}
