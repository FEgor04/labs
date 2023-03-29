package lab7.client.data

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab7.client.domain.RemoteCommandHandler
import lab7.client.exceptions.ClientException
import lab7.config.SharedConfig
import lab7.entities.dtos.requests.*
import lab7.entities.dtos.responses.*
import lab7.entities.user.User
import lab7.entities.vehicle.Vehicle
import lab7.entities.vehicle.VehicleType
import lab7.logger.KCoolLogger
import lab7.udp.BetterDatagramChannel
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.nio.charset.Charset
import kotlin.time.ExperimentalTime

@Suppress("TooManyFunctions")
class UDPBetterChannelService(
    var user: User,
    private val nodeAddress: SocketAddress
) :
    RemoteCommandHandler {
    private val logger by KCoolLogger()
    private val dc = BetterDatagramChannel(
        DatagramChannel.open(),
        SharedConfig.ACK_BYTES,
        SharedConfig.CHUNK_SIZE,
        SharedConfig.UDP_FINAL_CHUNK,
        BIG_FUCKING_BUFFER,
    )

    fun tryToAuth(): User {
        logger.info("Trying to auth")
        val response = sendAndReceiveCommand(AuthRequestDTO(user))
        return (response as AuthResponseDTO).user
    }

    /**
     * Отправляет данные на сервер и получает ответ.
     */
    @OptIn(ExperimentalTime::class)
    private fun sendAndReceiveData(byteArray: ByteArray): String {
        val serverAddr = nodeAddress
        dc.connect(serverAddr)
        runBlocking { dc.send(ByteBuffer.wrap(byteArray), serverAddr) }
        logger.info("Sent to data server. Awaiting for data.")
        val data: String = receiveData().trim()
        dc.disconnect()
        return data
    }

    /**
     * Получает данные от сервера
     * @param bufferSize размер буфера для данных
     */
    private fun receiveData(): String = runBlocking {
        val (data, _) = dc.receive()
        logger.info("Received all chunks. Data size: ${data.size}")
        data.toString(Charset.forName("UTF-8"))
    }

    private fun sendAndReceiveString(data: String): String {
        val resp = sendAndReceiveData(data.toByteArray(Charset.forName("UTF-8"))).trim()
        return resp
    }

    private fun sendAndReceiveCommand(request: RequestDTO): ResponseDTO {
        val responseStr = sendAndReceiveString(Json.encodeToString(request)).trim()
        val response: ResponseDTO = Json.decodeFromString(responseStr)
        if (response.error != null) {
            if (request.name == "auth") {
                throw ClientException.AuthException(response.error!!)
            }
            throw ClientException.CommandException(request.name, response.error!!)
        }
        return response
    }

    override fun add(vehicle: Vehicle): Int {
        val response = sendAndReceiveCommand(AddRequestDTO(vehicle.copy(authorID = user.id), user))
        return (response as AddResponseDTO).newId!!
    }

    override fun show(): List<Vehicle> {
        return (sendAndReceiveCommand(ShowRequestDTO(user)) as ShowResponseDTO).vehicles
    }

    override fun clear() {
        sendAndReceiveCommand(ClearRequestDTO(user))
    }

    override fun countByType(type: VehicleType?): Int {
        return (sendAndReceiveCommand(CountByTypeRequestDTO(type, user)) as CountByTypeResponseDTO).count
    }

    override fun countLessThanEnginePower(power: Double): Int {
        return (
            sendAndReceiveCommand(
                CountLessThanEnginePowerRequestDTO(
                    power,
                    user
                )
            ) as CountLessThanEnginePowerResponseDTO
            ).count
    }

    override fun getCollectionInfo(): InfoResponseDTO {
        return (sendAndReceiveCommand(InfoRequestDTO(user))) as InfoResponseDTO
    }

    override fun getMinById(): Vehicle? {
        return (sendAndReceiveCommand(GetMinByIDRequestDTO(user)) as GetMinByIDResponseDTO).min
    }

    override fun removeGreater(vehicle: Vehicle): Int {
        return (
            sendAndReceiveCommand(
                RemoveGreaterRequestDTO(
                    vehicle.copy(authorID = user.id),
                    user
                )
            ) as RemoveGreaterResponseDTO
            ).cnt
    }

    override fun remove(id: Int) {
        (sendAndReceiveCommand(RemoveRequestDTO(id, user)))
    }

    override fun removeLower(veh: Vehicle): Int {
        return (
            sendAndReceiveCommand(
                RemoveLowerRequestDTO(
                    veh.copy(authorID = user.id),
                    user
                )
            ) as RemoveLowerResponseDTO
            ).cnt
    }

    override fun replaceIfLower(id: Int, element: Vehicle): ReplaceIfLowerResults {
        return (
            sendAndReceiveCommand(
                ReplaceIfLowerRequestDTO(
                    id,
                    element.copy(authorID = user.id),
                    user
                )
            ) as ReplaceIfLowerResponseDTO
            ).result
    }

    override fun updateVehicleById(newVehicle: Vehicle) {
        sendAndReceiveCommand(
            UpdateVehicleByIdRequestDTO(
                id = newVehicle.id,
                newVehicle.copy(authorID = user.id),
                user
            )
        )
    }

    override fun exit() {
        dc.disconnect()
        dc.close()
    }

    override fun await() {
        sendAndReceiveCommand(DelayRequestDTO(user))
    }

    companion object {
        private const val BIG_FUCKING_BUFFER = 5000
    }
}
