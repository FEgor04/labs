package lab7.lb.data.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import lab7.entities.dtos.requests.RequestDTO
import lab7.logger.KCoolLogger
import lab7.udp.BetterDatagramSocket
import lab7.udp.FlowAcknowledgeProvider
import java.net.InetSocketAddress
import java.nio.charset.Charset

fun CoroutineScope.produceLoadBalancerDatagram(
    producer: UDPProducer,
    channel: SendChannel<Pair<RequestDTO, InetSocketAddress>>,
) =
    launch {
        val logger by KCoolLogger()
        while (true) {
            try {
                val (request, addr) = producer.take()
                channel.send(Pair(request, addr))
            } catch (_: lab7.exceptions.ServerException.AcknowledgePseudoException) {
            }
        }
    }

class UDPProducer(
    private val ds: BetterDatagramSocket,
    private val acknowledger: FlowAcknowledgeProvider,
) {
    private val logger by KCoolLogger()

    private suspend fun receiveData(): Pair<String, InetSocketAddress> {
        val (data, addr) = ds.receive()
        val dataStr: String = data.toString(Charset.forName("UTF-8"))
        if (dataStr == "ACK") {
            acknowledger.sendAcknowledge(addr)
            throw lab7.exceptions.ServerException.AcknowledgePseudoException()
        }
        return Pair(dataStr, addr as InetSocketAddress)
    }

    suspend fun take(): Pair<RequestDTO, InetSocketAddress> {
        val (dataStr, addr) = receiveData()
        return Pair(stringToRequest(dataStr), addr)
    }

    private fun stringToRequest(s: String): RequestDTO {
        return Json.decodeFromString(s.replace((0).toChar().toString(), ""))
    }
}
