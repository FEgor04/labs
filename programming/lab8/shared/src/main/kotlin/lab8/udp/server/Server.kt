package lab8.udp.server

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import lab8.config.UDPConfiguration
import lab8.udp.acknowledge.FlowAcknowledgeProvider
import lab8.udp.communicator.UDPCommunicator
import java.net.SocketAddress

open class Server<T, V>(
    private val communicator: UDPCommunicator,
    private val config: UDPConfiguration,
    private val consumersNumber: Int,
    private val producersNumber: Int,
    private val workesrNumber: Int,
    private val handler: RequestsHandler<T, V>,
    private val serializer: (String) -> T,
    private val deserializer: (V) -> String,
) {

    private val acknowledgeProvider = FlowAcknowledgeProvider.generate()
    private val requestsChannel = Channel<Pair<T, SocketAddress>>()
    private val responsesChannel = Channel<Pair<V, SocketAddress>>()
    private val producer = Producer<T>(communicator, acknowledgeProvider, config.ackBytes, serializer)
    private val consumer = Consumer<V>(communicator, deserializer)

    suspend fun run() = coroutineScope {
        repeat(producersNumber) {
            this.produceDatagram(producer, requestsChannel)
        }

        repeat(workesrNumber) {
            this.handleRequest(handler, requestsChannel, responsesChannel)
        }

        repeat(consumersNumber) {
            this.consumeDatagram(consumer, responsesChannel)
        }
    }
}
