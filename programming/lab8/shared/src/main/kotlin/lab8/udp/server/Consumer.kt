package lab8.udp.server

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import lab8.logger.KCoolLogger
import lab8.udp.communicator.UDPCommunicator
import java.net.SocketAddress
import java.nio.charset.Charset

fun <V> CoroutineScope.consumeDatagram(
    consumer: Consumer<V>,
    channel: ReceiveChannel<Pair<V, SocketAddress>>
) = launch {
    for (req in channel) {
        consumer.consume(req)
    }
}

@Suppress("MagicNumber")
class Consumer<V>(
    private val socket: UDPCommunicator,
    private val serializer: (V) -> String
) {
    private val logger by KCoolLogger()

    suspend fun consume(response: Pair<V, SocketAddress>) {
        val data = serializer(response.first).toByteArray(Charset.forName("UTF-8"))
        logger.info("Sending data to ${response.second}. Data size: ${data.size}")
        socket.send(data, response.second)
    }
}
