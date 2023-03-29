package lab7.udp

import lab7.config.SharedConfig
import java.nio.ByteBuffer
import kotlin.math.ceil

object UDPUtils {
    fun splitToChunks(data: ByteArray, chunkSize: Int = SharedConfig.CHUNK_SIZE): List<ByteArray> {
        val numberOfChunks = ceil(data.size.toFloat() / chunkSize).toInt()
        val chunks = (0 until numberOfChunks).map<Int, ByteArray> {
            if (it == numberOfChunks - 1) {
                data.slice(it * chunkSize until data.size)
                    .toByteArray()
            } else {
                (
                    data.slice(it * chunkSize until (it + 1) * chunkSize)
                        .toByteArray() +
                        SharedConfig.UDP_HAS_NEXT_CHUNK
                    )
            }
        }.toList()
        return chunks
    }

    /**
     * Получает данные, разбитые на чанки.
     * @param receive функция, возвращающая следующий чанк
     * @param chunkSize размер чанка
     * @param shouldWaitForNextByte байт в конце сообщения, означающий, что стоит ждать следующего чанка
     */
    suspend fun receiveChunkedData(
        shouldWaitForNextByte: Byte = SharedConfig.UDP_HAS_NEXT_CHUNK,
        chunkSize: Int = SharedConfig.CHUNK_SIZE,
        receive: suspend () -> ByteArray
    ): ByteArray {
        var data = byteArrayOf()
        while (true) {
            val chunk = receive()
            if (chunk.last() != shouldWaitForNextByte) {
                data += chunk.take(chunkSize)
                break
            } else {
                data += chunk.take(chunkSize)
            }
        }
        return data
    }

    fun bufferToByteArray(buffer: ByteBuffer): ByteArray {
        buffer.flip()
        val data = ByteArray(buffer.remaining())
        buffer.get(data)
        return data
    }
}
