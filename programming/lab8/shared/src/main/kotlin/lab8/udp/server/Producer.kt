package lab8.udp.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import lab8.exceptions.ServerException
import lab8.logger.KCoolLogger
import lab8.udp.acknowledge.AcknowledgeProvider
import lab8.udp.communicator.UDPCommunicator
import java.net.SocketAddress
import java.nio.charset.Charset

fun <T> CoroutineScope.produceDatagram(producer: Producer<T>, channel: SendChannel<Pair<T, SocketAddress>>) =
    launch {
        while (true) {
            try {
                channel.send(producer.take())
            } catch (_: ServerException.AcknowledgePseudoException) {
            }
        }
    }

class Producer<T>(
    private val producer: UDPCommunicator,
    private val acknowledgeProvider: AcknowledgeProvider,
    private val ackBytes: ByteArray,
    private val deserializer: (String) -> T,
) {

    companion object {
        val logger by KCoolLogger()
    }

    private suspend fun receiveData(): Pair<String, SocketAddress> {
        logger.info("Waiting for data")
        val (data, address) = producer.receive()
        if (data.contentEquals(ackBytes)) {
            acknowledgeProvider.acknowledge(address)
            throw ServerException.AcknowledgePseudoException()
        }
        val dataStr = data.toString(Charset.forName("UTF-8"))

        return Pair(dataStr.replace((0).toChar().toString(), ""), address)
    }

    suspend fun take(): Pair<T, SocketAddress> {
        val (dataStr, addr) = receiveData()
        return Pair(deserializer(dataStr), addr)
    }
}
