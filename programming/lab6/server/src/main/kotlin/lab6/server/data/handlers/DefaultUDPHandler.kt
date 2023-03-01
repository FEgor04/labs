package lab6.server.data.handlers

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import lab6.server.data.handlers.commands.*
import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.NoSuchCommandResponseDTO
import lab6.shared.entities.dtos.responses.ResponseDTO
import lab6.shared.logger.KCoolLoggerDelegate
import lab6.shared.logger.koolLogger
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.charset.Charset


/**
 * Абстрактный класс обработчика UDP-запросов
 */
abstract class DefaultUDPHandler(private val usecase: CommandsHandlerUseCase) {
    abstract val addr: InetSocketAddress

    companion object {
        private val logger by KCoolLoggerDelegate()
    }

    private var shouldContinue = true

    private val commands: ArrayList<CommandHandler> = arrayListOf(
        AddHandler(usecase),
        ShowHandler(usecase),
        CountByTypeCommand(usecase),
        ClearHandler(usecase),
        CountLessThanEnginePowerCommand(usecase),
        GetMinByIdCommand(usecase),
        InfoCommand(usecase),
        RemoveGreaterCommand(usecase),
        RemoveCommand(usecase),
        RemoveLowerCommand(usecase),
        ReplaceIfLowerCommand(usecase),
        UpdateCommand(usecase),
    )

    /**
     * Получает данные с клиента.
     * Возвращает пару из данных и адреса клиента
     */
    abstract fun receiveData(bufferSize: Int = 4 * 1024 * 8): Pair<String, SocketAddress>


    /**
     * Отправляет данные клиенту
     */
    abstract fun sendData(data: String, addr: SocketAddress)

    /**
     * Устаналвивает соединение с клиентом.
     * После этого даннные можно получать и отправлять только ему / от него
     */
    abstract fun connectToClient(addr: SocketAddress)

    /**
     * Отключается от клиента
     */
    abstract fun disconnectFromClient()

    /**
     * Закрывает датаграмму
     */
    abstract fun close()

    fun run() {
        logger.info ("Started server at $addr")

        while (shouldContinue) {
            val (dataFromClient, clientAddr) = receiveData()

            val request: RequestDTO = try {
                Json.decodeFromString<RequestDTO>(dataFromClient.replace((0).toChar().toString(), ""))
            }
            catch (e: Exception) {
                logger.error ("Could not parse JSON. Error: $e")
                val data = "Bad data, bitch!".toByteArray(Charset.forName("UTF-8"))
                sendData("Bad data, bitch!", clientAddr)
                continue
            }

            logger.info ( "Handling $request from ${clientAddr}. User: ${request.user}")
            var response: ResponseDTO? = null
            try {
                response = commands.find { it.check(request) }?.handle(request)
            } catch (e: Exception) {
                logger.error("Error while executing command : $e")
            }
            logger.info ("Handler response is $response" )
            val toReturn = Json.encodeToString(response ?: NoSuchCommandResponseDTO(request.name))
            logger.info ( "Response is $toReturn" )

            sendData(toReturn, clientAddr)
            logger.info("Sent response to client $clientAddr")
            disconnectFromClient()
            logger.info ( "Disconnecetd from client $clientAddr" )
        }
        close()
    }

    /**
     * Прекращает обработку udp-запросов
     */
    fun stop() {
        disconnectFromClient()
        close()
        shouldContinue = false
    }
}