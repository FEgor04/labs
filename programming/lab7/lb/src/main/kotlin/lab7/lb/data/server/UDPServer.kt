package lab7.lb.data.server

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import lab7.config.SharedConfig
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.ResponseDTO
import lab7.lb.domain.usecase.BalancerUseCase
import lab7.logger.KCoolLogger
import lab7.udp.BetterDatagramSocket
import lab7.udp.FlowAcknowledgeProvider
import java.net.DatagramSocket
import java.net.InetSocketAddress

class UDPServer(private val useCase: BalancerUseCase, loadBalancerPort: Int) {
    val acknowledger = FlowAcknowledgeProvider.generate()
    private val socket = BetterDatagramSocket(
        DatagramSocket(loadBalancerPort),
        SharedConfig.ACK_BYTES,
        SharedConfig.CHUNK_SIZE,
        SharedConfig.UDP_FINAL_CHUNK,
    ) {
        acknowledger.waitForAck(it)
    }

    val requestsChannel = Channel<Pair<RequestDTO, InetSocketAddress>>()
    val responsesChannel = Channel<Pair<ResponseDTO, InetSocketAddress>>()

    private val balancer = UDPBalancer(usecase = useCase)
    private val producer = UDPProducer(socket, acknowledger)
    private val consumer = UDPConsumer(socket)
    private val logger by KCoolLogger()

    suspend fun run() = coroutineScope {
        produceLoadBalancerDatagram(producer, requestsChannel)
        balanceUDP(balancer, requestsChannel, responsesChannel)
        consumeLoadBalancerResponses(consumer, responsesChannel)
    }
}
