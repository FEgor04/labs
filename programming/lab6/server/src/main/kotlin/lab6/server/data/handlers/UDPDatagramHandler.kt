package lab6.server.data.handlers

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.logger.KCoolLoggerDelegate
import lab6.shared.logger.koolLogger
import java.net.*
import java.nio.charset.Charset


/**
 * Обработчик запросов, использующий Datagram
 */
class UDPDatagramHandler(port: Int = 1234, private val usecase: CommandsHandlerUseCase): DefaultUDPHandler(usecase) {
    override val addr = InetSocketAddress(port)
    private val ds = DatagramSocket(port)

    companion object {
        private val logger by KCoolLoggerDelegate()
    }

    init {
        logger.error("ERROR")
    }

    override fun receiveData(bufferSize: Int): Pair<String, SocketAddress> {
        val data = ByteArray(bufferSize)
        val dp = DatagramPacket(data, data.size)
        ds.receive(dp)
        val dataStr: String = dp.data.toString(Charset.forName("UTF-8"))
        logger.info ( "Received \"$dataStr\" from ${dp.address}" )
        return Pair(dataStr, dp.socketAddress)
    }

    override fun sendData(data: String, addr: SocketAddress) {
        val dataByteArray = data.toByteArray(Charset.forName("UTF-8"))
        val dp = DatagramPacket(dataByteArray, dataByteArray.size, addr)
        ds.send(dp)
    }

    override fun connectToClient(addr: SocketAddress) {
        ds.connect(addr)
    }

    override fun disconnectFromClient() {
        ds.disconnect()
    }

    override fun close() {
        ds.close()
    }
}