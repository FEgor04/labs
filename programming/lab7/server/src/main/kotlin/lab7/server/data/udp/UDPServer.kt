package lab7.server.data.udp

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab7.config.SharedConfig
import lab7.entities.dtos.requests.RegisterServerRequest
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.RegisterServerResponse
import lab7.entities.dtos.responses.ResponseDTO
import lab7.logger.KCoolLogger
import lab7.server.config.ServerConfiguration
import lab7.server.data.handlers.Executor
import lab7.server.data.handlers.handleRequest
import lab7.server.domain.usecases.CommandsHandlerUseCase
import lab7.udp.BetterDatagramSocket
import lab7.udp.FlowAcknowledgeProvider
import java.net.DatagramSocket
import java.net.SocketAddress
import java.nio.charset.Charset

class UDPServer(
    port: Int,
    private val useCase: CommandsHandlerUseCase,
    private val config: ServerConfiguration.UDPConfiguration,
    private val loadBalancerAddr: SocketAddress,
) {
    companion object {
        private val logger by KCoolLogger()
    }

    private val acknowledgeProvider = FlowAcknowledgeProvider.generate()

    private val stupidSocket = DatagramSocket(port)

    private val socket = BetterDatagramSocket(
        stupidSocket,
        SharedConfig.ACK_BYTES,
        config.chunkSize,
        SharedConfig.UDP_FINAL_CHUNK,
    ) { wantedAddr ->
        acknowledgeProvider.waitForAck(wantedAddr)
    }
    private val producer = DatagramProducer(socket, acknowledgeProvider)
    private val consumer = DatagramConsumer(socket)

    private val requestsChannel = Channel<Pair<RequestDTO, SocketAddress>>(Channel.UNLIMITED)
    private val responseChannel = Channel<Pair<ResponseDTO, SocketAddress>>(Channel.UNLIMITED)
    private val executor = Executor(useCase)

    suspend fun run() = coroutineScope {
        registerToLoadBalancer()

        repeat(config.producersNumber) {
            this.produceDatagram(producer, requestsChannel)
        }

        repeat(config.workersNumber) {
            this.handleRequest(executor, requestsChannel, responseChannel)
        }
        repeat(config.consumersNumber) {
            this.consumeDatagram(consumer, responseChannel)
        }
    }

    private suspend fun registerToLoadBalancer() {
        val autoAckSocket = BetterDatagramSocket(
            stupidSocket,
            SharedConfig.ACK_BYTES,
            config.chunkSize,
            SharedConfig.UDP_FINAL_CHUNK,
        )

        logger.info("Registering to load balancer")
        val request = RegisterServerRequest()
        val requestByteArray = Json.encodeToString<RequestDTO>(request)
            .toByteArray(Charset.forName("UTF-8"))

        autoAckSocket.send(requestByteArray, loadBalancerAddr)
        logger.info("Sent request to register. Waiting for response")

        logger.info("Waiting for response from load balancer")
        val responseByteArray = autoAckSocket.receive()
        val responseStr = responseByteArray.first.toString(Charset.forName("UTF-8"))
        logger.info("Response from load balancer: $responseStr")
        val response =
            Json.decodeFromString<ResponseDTO>(
                responseStr
            ) as RegisterServerResponse

        logger.info("Registered at load balancer. My number is ${response.number}.")
    }
}
