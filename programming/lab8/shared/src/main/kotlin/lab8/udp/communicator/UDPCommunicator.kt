package lab8.udp.communicator

import java.net.SocketAddress

/**
 * Интерфейс для коммуникации по UDP
 */
interface UDPCommunicator {
    /**
     * Отправляет данные на указанный адрес
     */
    suspend fun send(data: ByteArray, addr: SocketAddress)

    /**
     * Принимает данные
     */
    suspend fun receive(): Pair<ByteArray, SocketAddress>
}
