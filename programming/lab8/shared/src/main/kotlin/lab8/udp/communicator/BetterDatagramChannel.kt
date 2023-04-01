package lab8.udp.communicator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import lab8.logger.KCoolLogger
import lab8.udp.acknowledge.AcknowledgeProvider
import java.lang.Integer.min
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Канал датаграмм с разбиением на чанки, отправкой ACK-сообщения
 * @param channel канал, с помощью которого будет производиться отправка
 * @param ackBytes байты, которые будут отправлены в качестве ACK-сообщения
 * @param chunkSize размер чанка
 * @param finalChunkByte байт, означающий, что данный чанк -- последний
 * @param acknowledgeProvider функция, блокирующая исполнение пока не будет получен ACK от клиента.
 * По умолчанию равен пустой функции, то есть ACK не будет ожидаться.
 * @param acknowledgeTimeout таймаут на получение ACK
 */
class BetterDatagramChannel(
    private val channel: DatagramChannel,
    private val ackBytes: ByteArray,
    private val chunkSize: Int,
    private val finalChunkByte: Byte,
    private val totalBufferSizeMultiplier: Int = 10,
    private val sendChunkTimeout: Duration = 5.seconds,
    private val acknowledgeTimeout: Duration = 5.seconds,
    private val acknowledgeProvider: AcknowledgeProvider? = null,
) : UDPCommunicator {
    private val ackBuffer = ByteBuffer.wrap(ackBytes + finalChunkByte)
    private val logger by KCoolLogger()
    private val dummyBuffer = ByteBuffer.allocate(dummyBufferSize)
    private val totalBuffer = ByteBuffer.allocate(totalBufferSizeMultiplier * chunkSize)

    /**
     * Отправляет содержимое буфера на указанный адрес.
     * При необходимости разобьет буфер на чанки.
     * @param buf Буфер для отправк
     * @param addr Адрес для отправки
     * @return количество отправленных байт
     */
    suspend fun send(buf: ByteBuffer, addr: SocketAddress): Int {
        var totalNumberOfBytesSent = 0
        val chunksCount: Int = (buf.remaining() + chunkSize - 1) / chunkSize
        for (i in 0 until chunksCount) {
            val curByteBufferSize = if (i != chunksCount - 1) {
                chunkSize
            } else {
                buf.remaining() + 1
            }
            val cur = ByteArray(curByteBufferSize)
            val curBuf = ByteBuffer.wrap(cur)
            buf.get(cur, 0, min(buf.remaining(), chunkSize))
            if (i == chunksCount - 1) {
                cur[cur.lastIndex] = finalChunkByte
            }
            logger.info("Sending chunk #${i + 1}")
            totalNumberOfBytesSent += withTimeout(sendChunkTimeout) {
                withContext(Dispatchers.IO) {
                    channel.send(curBuf, addr)
                }
            }

            if (i < chunksCount - 1) {
                logger.info("Waiting for ACK for chunk #${i + 1}")
                withTimeout(acknowledgeTimeout) {
                    if (acknowledgeProvider == null) {
                        receiveAck()
                    } else {
                        acknowledgeProvider.waitForAcknowledge(addr)
                    }
                }
                logger.info("Received ACK for chunk #${i + 1}")
            }
        }
        return totalNumberOfBytesSent
    }

    override suspend fun send(data: ByteArray, addr: SocketAddress) {
        this.send(ByteBuffer.wrap(data), addr)
    }

    /**
     * Получает данные
     */
    override suspend fun receive(): Pair<ByteArray, SocketAddress> {
        totalBuffer.clear()
        var addr: SocketAddress
        var chunkNumber = 1
        while (true) {
            logger.info("waiting for chunk #$chunkNumber")
            addr = withTimeout(this.acknowledgeTimeout) {
                withContext(Dispatchers.IO) {
                    channel.receive(totalBuffer)
                }
            }
            chunkNumber++
            val lastByte = totalBuffer[totalBuffer.position() - 1]
            logger.info("Last byte is $lastByte")
            if (lastByte == finalChunkByte) {
                logger.info("Breaking the Habits")
                break
            } else {
                sendAck(addr)
                logger.info("Sent ACK for chunk #$chunkNumber")
            }
        }
        return Pair(totalBuffer.array().take(totalBuffer.position() - 1).toByteArray(), addr)
    }

    private suspend fun sendAck(addr: SocketAddress) {
        ackBuffer.rewind()
        withContext(Dispatchers.IO) {
            channel.send(ackBuffer, addr)
        }
    }

    private suspend fun receiveAck() {
        logger.info("DEFAULT ACK: Waiting for ACK")
        withContext(Dispatchers.IO) {
            channel.receive(dummyBuffer)
        }
    }

    fun connect(addr: SocketAddress) = channel.connect(addr)
    fun disconnect() = channel.disconnect()

    fun close() = channel.close()

    companion object {
        private const val dummyBufferSize = 10
    }
}
