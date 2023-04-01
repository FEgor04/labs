package lab8.udp.communicator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lab8.logger.KCoolLogger
import lab8.udp.acknowledge.AcknowledgeProvider
import java.lang.Integer.min
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketAddress
import kotlin.math.ceil

/**
 *
 */
class BetterDatagramSocket(
    private val socket: DatagramSocket,
    private val ackBytes: ByteArray,
    private val chunkSize: Int,
    private val isFinalChunkByte: Byte,
    private val acknowledgeProvider: AcknowledgeProvider? = null,
) : UDPCommunicator {
    private val logger by KCoolLogger()
    private val ackArray = ByteArray(ackArraySize)

    /**
     * Отправялет массив байтов на указанный адрес.
     * При необходимости разибвает на чанки и ожидает получения ACK.
     * @param data данные для передачи
     * @param addr адрес передачи
     * @throws kotlinx.coroutines.TimeoutCancellationException при таймауте ожидания ACK
     */
    override suspend fun send(data: ByteArray, addr: SocketAddress) {
        val chunksNumber = ceil(data.size.toFloat() / chunkSize).toInt()
        logger.info("Sending data to $addr. TOTAL CHUNKS NUMBER: $chunksNumber. TOTAL DATA SIZE: ${data.size}")
        var buf: ByteArray // один общий для более эффективного взаимодействия с памятью
        for (i in 0 until chunksNumber) {
            buf = data.copyOfRange(i * chunkSize, min((i + 1) * chunkSize, data.size))
            val packet: DatagramPacket
            if (i == chunksNumber - 1) {
                buf += isFinalChunkByte
                packet = DatagramPacket(buf, buf.size, addr)
            } else {
                packet = DatagramPacket(buf, buf.size, addr)
            }
            withContext(Dispatchers.IO) {
                socket.send(packet)
            }
            logger.info("Last byte is ${buf.last()}")
            if (i < chunksNumber - 1) {
                logger.info("Sent chunk #${i + 1}. Waiting for ACK.")
                if (acknowledgeProvider != null) {
                    acknowledgeProvider.waitForAcknowledge(addr)
                } else {
                    receiveAck()
                }
                logger.info("Sent chunk #${i + 1} and received ACK for it")
            }
        }
        logger.info("SENT ALL TO $addr")
    }

    /**
     * Получает данные. Блокирует поток пока данные не будут получены.
     * Автоматически сливает чанкованные данные воедино и отправляет ACK сигнал
     */
    override suspend fun receive(): Pair<ByteArray, SocketAddress> {
        var totalData = ByteArray(0)
        val curData = ByteArray(chunkSize + 1)
        var socketAddress: SocketAddress?
        var chunkNumber = 0
        while (true) {
            chunkNumber++
            logger.info("Receiving chunk #$chunkNumber")
            val dp = DatagramPacket(curData, curData.size)
            withContext(Dispatchers.IO) {
                socket.receive(dp)
            }
            logger.info("Seding ACK for chunk #$chunkNumber.")
            socketAddress = dp.socketAddress
            if (dp.data[dp.length - 1] == isFinalChunkByte) {
                totalData += dp.data.take(dp.length - 1)
                break
            } else {
                totalData += dp.data.take(dp.length)
                sendAck(socketAddress)
            }
            logger.info("Send ACK for chunk #$chunkNumber")
        }
        return Pair(totalData, socketAddress!!)
    }

    /**
     * Отправляет ACK-сигнал на указанный адрес
     */
    private suspend fun sendAck(addr: SocketAddress) {
        logger.info("ACKSENDER: Sending ACK to $addr")
        val dp = DatagramPacket(ackBytes + isFinalChunkByte, ackBytes.size + 1, addr)
        withContext(Dispatchers.IO) {
            socket.send(dp)
        }
        logger.info("ACKSENDER: Sent AKC to $addr")
    }

    private suspend fun receiveAck() {
        logger.info("Default ACK: Waiting for ACK")
        val dp = DatagramPacket(ackArray, ackArray.size)
        withContext(Dispatchers.IO) {
            socket.receive(dp)
        }
    }

    fun connect(addr: SocketAddress) {
        socket.connect(addr)
    }

    fun disconnect() = socket.disconnect()

    fun close() = socket.close()

    companion object {
        private const val ackArraySize = 100
    }
}
