package lab8.server.data.udp

import io.prometheus.client.Summary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import lab8.entities.dtos.requests.RequestDTO
import lab8.exceptions.ServerException
import lab8.logger.KCoolLogger
import lab8.udp.acknowledge.FlowAcknowledgeProvider
import lab8.udp.communicator.BetterDatagramSocket
import java.net.SocketAddress
import java.nio.charset.Charset

fun CoroutineScope.produceDatagram(producer: DatagramProducer, channel: SendChannel<Pair<RequestDTO, SocketAddress>>) =
    launch {
        while (true) {
            try {
                channel.send(producer.take())
            } catch (_: ServerException.AcknowledgePseudoException) {
            }
        }
    }

/**
 * Обработчик запросов, использующий Datagram
 */
@Suppress("MagicNumber")
class DatagramProducer(
    private val ds: BetterDatagramSocket,
    private val acknowledgeProvider: FlowAcknowledgeProvider,
//    private val channel: Channel<Pair<RequestDTO, SocketAddress>>
) {
    private var shouldContinue = true
    private val bytesReceivedSummary = Summary.build()
        .name("request_size_bytes")
        .help("Request size in bytes.")
        .quantile(0.5, 0.05)
        .quantile(0.9, 0.01)
        .quantile(0.99, 0.001)
        .register()

    companion object {
        private val logger by KCoolLogger()
    }

    private suspend fun receiveData(): Pair<String, SocketAddress> {
        logger.info("Waiting for data")
        val (data, address) = ds.receive()

        val dataStr = data.toString(Charset.forName("UTF-8"))

        if (dataStr == "ACK") {
            acknowledgeProvider.acknowledge(address)
            throw ServerException.AcknowledgePseudoException()
        }

        // Observe only not-ACK requests
        bytesReceivedSummary.observe(data.size.toDouble())

        return Pair(dataStr.replace((0).toChar().toString(), ""), address)
    }

    suspend fun take(): Pair<RequestDTO, SocketAddress> {
        val (dataStr, addr) = receiveData()
        return Pair(stringToRequest(dataStr), addr)
    }

    private fun stringToRequest(s: String): RequestDTO {
        return Json.decodeFromString(s.replace((0).toChar().toString(), ""))
    }

//
//    suspend fun run() {
//        while (shouldContinue) {
//            val (dataStr, addr) = receiveData(DEFAULT_BUFFER_SIZE)
//            channel.send(Pair(stringToRequest(dataStr), addr))
//        }
//    }

    fun stop() {
        shouldContinue = false
    }
}
