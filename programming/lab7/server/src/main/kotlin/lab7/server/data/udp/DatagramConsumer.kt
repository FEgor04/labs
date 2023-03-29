package lab7.server.data.udp

import io.prometheus.client.Summary
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab7.config.SharedConfig
import lab7.entities.dtos.responses.ResponseDTO
import lab7.logger.KCoolLogger
import lab7.udp.BetterDatagramSocket
import java.net.SocketAddress
import java.nio.charset.Charset
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun CoroutineScope.consumeDatagram(
    consumer: DatagramConsumer,
    channel: ReceiveChannel<Pair<ResponseDTO, SocketAddress>>
) = launch {
    for (req in channel) {
        consumer.consume(req)
    }
}

@Suppress("MagicNumber")
class DatagramConsumer(
    private val socket: BetterDatagramSocket,
) {
    private val logger by KCoolLogger()
    private val responseLatency = Summary.build()
        .name("response_latency_seconds")
        .help("Response latency in seconds.")
        .quantile(0.5, 0.05)
        .quantile(0.9, 0.01)
        .quantile(0.99, 0.001)
        .register()
    private val bytesSentSummary = Summary.build()
        .name("response_size_bytes")
        .help("Response size in bytes.")
        .quantile(0.5, 0.05)
        .quantile(0.9, 0.01)
        .quantile(0.99, 0.001)
        .register()

    @OptIn(ExperimentalTime::class)
    suspend fun consume(response: Pair<ResponseDTO, SocketAddress>) {
        val data = requestToByteArray(response.first)
        logger.info("Sending data to ${response.second}. Data size: ${data.size}")

        val responseTimer = responseLatency.startTimer()
        val time = measureTime {
            socket.send(data, response.second)
        }
        bytesSentSummary.observe(data.size.toDouble())
        responseTimer.observeDuration()
        logger.info(
            "Successfully sent data to ${response.second}. " +
                "Total size: ${"%.2f".format(data.size.toFloat() / SharedConfig.BYTES_IN_MEGABYTE)} MiB. " +
                "Transfer speed: ${
                "%.2f".format(
                    data.size.toFloat() / SharedConfig.BYTES_IN_MEGABYTE / time.toDouble(
                        DurationUnit.SECONDS
                    )
                )
                } MiB/s"
        )
    }

    private fun requestToByteArray(req: ResponseDTO): ByteArray {
        val dataStr = Json.encodeToString(req)
        return dataStr.toByteArray(Charset.forName("UTF-8"))
    }
}
