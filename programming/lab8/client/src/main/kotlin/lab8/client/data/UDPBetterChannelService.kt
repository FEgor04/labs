package lab8.client.data

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab8.client.config.ClientConfiguration
import lab8.client.domain.RemoteCommandHandler
import lab8.client.exceptions.ClientException
import lab8.entities.dtos.requests.*
import lab8.entities.dtos.responses.*
import lab8.entities.user.User
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType
import lab8.exceptions.ServerException
import lab8.logger.KCoolLogger
import lab8.udp.communicator.BetterDatagramChannel
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.nio.charset.Charset

@Suppress("TooManyFunctions")
class UDPBetterChannelService(
    override var user: User,
    private val configuration: ClientConfiguration
) :
    RemoteCommandHandler {
    private val logger by KCoolLogger()
    private val dc = BetterDatagramChannel(
        DatagramChannel.open(),
        configuration.udpConfig.ackBytes,
        configuration.udpConfig.chunkSize,
        configuration.udpConfig.isFinalChunkByte,
        BIG_FUCKING_BUFFER,
    )

    private suspend fun tryToAuth(): User {
        logger.info("Trying to auth")
        val response = sendAndReceiveCommand(AuthRequestDTO(user))
        return (response as AuthResponseDTO).user
    }

    /**
     * Отправляет данные на сервер и получает ответ.
     */
    private suspend fun sendAndReceiveData(byteArray: ByteArray): String {
        val serverAddr = configuration.loadBalancerAddr
        dc.connect(serverAddr)
        dc.send(ByteBuffer.wrap(byteArray), serverAddr)
        logger.info("Sent to data server. Awaiting for data.")
        val data: String = receiveData().trim()
        dc.disconnect()
        return data
    }

    /**
     * Получает данные от сервера
     */
    private suspend fun receiveData(): String {
        val (data, _) = runBlocking { dc.receive() }
        logger.info("Received all chunks. Data size: ${data.size}")
        return data.toString(Charset.forName("UTF-8"))
    }

    private suspend fun sendAndReceiveString(data: String): String {
        val resp = sendAndReceiveData(data.toByteArray(Charset.forName("UTF-8"))).trim()
        return resp
    }

    private suspend fun sendAndReceiveCommand(request: RequestDTO): ResponseDTO {
        val responseStr = sendAndReceiveString(Json.encodeToString(request)).trim()
        val response: ResponseDTO = Json.decodeFromString(responseStr)
        if (response.error != null) {
            throw exceptionMapper(response.error!!)
        }
        return response
    }

    override suspend fun add(vehicle: Vehicle): Int {
        val response = sendAndReceiveCommand(AddRequestDTO(vehicle.copy(authorID = user.id), user))
        return (response as AddResponseDTO).newId!!
    }

    override suspend fun show(): List<Vehicle> {
        return (sendAndReceiveCommand(ShowRequestDTO(user)) as ShowResponseDTO).vehicles
    }

    override suspend fun clear() {
        sendAndReceiveCommand(ClearRequestDTO(user))
    }

    override suspend fun countByType(type: VehicleType?): Int {
        return (sendAndReceiveCommand(CountByTypeRequestDTO(type, user)) as CountByTypeResponseDTO).count
    }

    override suspend fun countLessThanEnginePower(power: Double): Int {
        return (
            sendAndReceiveCommand(
                CountLessThanEnginePowerRequestDTO(
                    power,
                    user
                )
            ) as CountLessThanEnginePowerResponseDTO
            ).count
    }

    override suspend fun getCollectionInfo(): InfoResponseDTO {
        return (sendAndReceiveCommand(InfoRequestDTO(user))) as InfoResponseDTO
    }

    override suspend fun getMinById(): Vehicle? {
        return (sendAndReceiveCommand(GetMinByIDRequestDTO(user)) as GetMinByIDResponseDTO).min
    }

    override suspend fun removeGreater(vehicle: Vehicle): Int {
        return (
            sendAndReceiveCommand(
                RemoveGreaterRequestDTO(
                    vehicle.copy(authorID = user.id),
                    user
                )
            ) as RemoveGreaterResponseDTO
            ).cnt
    }

    override suspend fun remove(id: Int) {
        (sendAndReceiveCommand(RemoveRequestDTO(id, user)))
    }

    override suspend fun removeLower(veh: Vehicle): Int {
        return (
            sendAndReceiveCommand(
                RemoveLowerRequestDTO(
                    veh.copy(authorID = user.id),
                    user
                )
            ) as RemoveLowerResponseDTO
            ).cnt
    }

    override suspend fun replaceIfLower(id: Int, element: Vehicle): ReplaceIfLowerResults {
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

    override suspend fun updateVehicleById(newVehicle: Vehicle) {
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

    override suspend fun await() {
        sendAndReceiveCommand(DelayRequestDTO(user))
    }

    override suspend fun tryToLogin(user: User): User {
        this.user = user
        this.user = tryToAuth()
        return this.user
    }

    override suspend fun createUser(user: User): User {
        this.user = (sendAndReceiveCommand(SignUpRequest(user)) as SignUpResponse).user
        return user
    }

    override suspend fun logout() {
        this.user = User("", "", -1)
    }

    companion object {
        private const val BIG_FUCKING_BUFFER = 5000
    }

    private fun exceptionMapper(e: String): Exception {
        if("this vehicle does not belong to you" in e) {
            return ServerException.BadOwnerException()
        }
        return Exception(e.toString())
    }
}
