package lab8.server.data.udp

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab8.entities.balancer.NodeAddress
import lab8.entities.dtos.requests.RegisterServerRequest
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.RegisterServerResponse
import lab8.entities.dtos.responses.ResponseDTO
import lab8.logger.KCoolLogger
import lab8.server.config.ServerConfiguration
import lab8.server.data.handlers.Executor
import lab8.server.domain.usecases.CommandsHandlerUseCase
import lab8.udp.acknowledge.FlowAcknowledgeProvider
import lab8.udp.communicator.BetterDatagramSocket
import lab8.udp.server.Server
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.nio.charset.Charset

class UDPServer(
    private val useCase: CommandsHandlerUseCase,
    private val configuration: ServerConfiguration,
) {
    private val logger by KCoolLogger()
    private val stupidSocket = DatagramSocket()
    private val flowAcknowledgeProvider = FlowAcknowledgeProvider.generate()
    private val betterDatagramSocket = BetterDatagramSocket(
        stupidSocket,
        configuration.udpConfiguration.ackBytes,
        configuration.udpConfiguration.chunkSize,
        configuration.udpConfiguration.isFinalChunkByte,
        flowAcknowledgeProvider,
    )

    private val executor = Executor(useCase)

    val server = Server<RequestDTO, ResponseDTO>(
        betterDatagramSocket,
        config = configuration.udpConfiguration,
        configuration.consumersNumber,
        configuration.workersNumber,
        configuration.producersNumber,
        executor,
        { Json.decodeFromString(it) },
        { Json.encodeToString(it) },
    )

    suspend fun run() {
        val serverAddress = stupidSocket.localSocketAddress as InetSocketAddress
        logger.info("Started server on addr: $serverAddress")
        registerToLoadBalancer()
        server.run()
    }

    private suspend fun registerToLoadBalancer() {
        val sendSocket = BetterDatagramSocket(
            DatagramSocket(),
            configuration.udpConfiguration.ackBytes,
            configuration.udpConfiguration.chunkSize,
            configuration.udpConfiguration.isFinalChunkByte,
        )
        val request = RegisterServerRequest(
            NodeAddress.fromInetSocketAddress(stupidSocket.localSocketAddress as InetSocketAddress)
        )
        val requestBytes = Json.encodeToString<RequestDTO>(request).toByteArray(Charset.forName("UTF-8"))
        sendSocket.send(requestBytes, configuration.loadBalancerAddress)
        val responseBytes = sendSocket.receive().first
        sendSocket.close()
        val response = Json.decodeFromString<ResponseDTO>(responseBytes.toString(Charset.forName("UTF-8")))
        logger.info("Got response from load balancer. My ID: ${(response as RegisterServerResponse).number}")
    }
}
