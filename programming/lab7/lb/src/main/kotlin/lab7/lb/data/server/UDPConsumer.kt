package lab7.lb.data.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab7.entities.dtos.responses.ResponseDTO
import lab7.logger.KCoolLogger
import lab7.udp.BetterDatagramSocket
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.charset.Charset

fun CoroutineScope.consumeLoadBalancerResponses(
    consumer: UDPConsumer,
    channel: ReceiveChannel<Pair<ResponseDTO, InetSocketAddress>>
) = launch {
    for (resp in channel) {
        consumer.consume(resp)
    }
}

class UDPConsumer(private val socket: BetterDatagramSocket) {
    private val logger by KCoolLogger()

    suspend fun consume(response: Pair<ResponseDTO, SocketAddress>) {
        val data = requestToByteArray(response.first)
        socket.send(data, response.second)
    }

    private fun requestToByteArray(req: ResponseDTO): ByteArray {
        return Json.encodeToString(req).toByteArray(Charset.forName("UTF-8"))
    }
}
