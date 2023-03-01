package lab6.client.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab6.client.domain.RemoteCommandHandler
import lab6.shared.entities.dtos.commands.*
import lab6.shared.entities.dtos.responses.*
import lab6.shared.entities.user.User
import lab6.shared.entities.vehicle.Vehicle
import lab6.shared.entities.vehicle.VehicleType
import lab6.shared.logger.koolLogger
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.nio.charset.Charset

class UDPHandlerService(host: InetAddress, port: Int = 1237, private val user: User): RemoteCommandHandler {
    private val addr = InetSocketAddress(host, port)
    private val logger by koolLogger()
    private val client = DatagramChannel.open().connect(addr)

    private val PACKAGE_SIZE = 4 * 1024 * 8

    init {
        client.configureBlocking(false)
        client.socket().soTimeout = 2000
        logger.info("Connected DatagramChannel to ${addr}")
    }

    /**
     * Отправляет данные на сервер и получает ответ.
     */
    private fun sendAndReceiveData(byteArray: ByteArray): String {
        logger.info("Sent data size to server.")

        val buffer = ByteBuffer.wrap(byteArray)
        client.send(buffer, addr)
        logger.info("Sent to data server. Awaiting for data size")

        val data = receiveData(PACKAGE_SIZE).trim()

        logger.info ("Received data from client. Data: $data" )

        return data
    }

    /**
     * Получает данные от сервера
     * @param bufferSize размер буфера для данных
     */
    private fun receiveData(bufferSize: Int = PACKAGE_SIZE): String {
        val buffer = ByteBuffer.allocate(bufferSize)
        var address: SocketAddress? = null
        while(address == null) {
            address = client.receive(buffer)
        }
        return extractMessage(buffer)
    }

    /**
     * Переводит сообщение, содержащееся в Buffer в строку
     */
    private fun extractMessage(buffer: ByteBuffer): String {
        buffer.flip()
        val data = ByteArray(buffer.remaining())
        buffer.get(data)
        return data.toString(Charset.forName("UTF-8"))
    }

    private fun sendAndReceiveString(data: String): String {
        val resp = sendAndReceiveData(data.toByteArray(Charset.forName("UTF-8"))).trim()
        return resp
    }

    private fun sendAndReceiveCommand(data: RequestDTO): ResponseDTO {
        val responseStr = sendAndReceiveString( Json.encodeToString(data) ).trim()
        logger.info ( "Response str: $responseStr" )
        val response: ResponseDTO = Json.decodeFromString(responseStr)
        if(response.error != null) {
            throw Exception(response.error)
        }
        return response
    }

    override fun add(vehicle: Vehicle): Int {
        val response = sendAndReceiveCommand(AddRequestDTO(vehicle, user))
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
        return (sendAndReceiveCommand(CountLessThanEnginePowerRequestDTO(power, user)) as CountLessThanEnginePowerResponseDTO).count
    }

    override fun getCollectionInfo(): InfoResponseDTO {
        return (sendAndReceiveCommand(InfoRequestDTO(user))) as InfoResponseDTO
    }

    override fun getMinById(): Vehicle? {
        return (sendAndReceiveCommand(GetMinByIDRequestDTO(user)) as GetMinByIDResponseDTO).min
    }

    override fun removeGreater(vehicle: Vehicle): Int {
        return (sendAndReceiveCommand(RemoveGreaterRequestDTO(vehicle, user)) as RemoveGreaterResponseDTO).cnt
    }

    override fun remove(id: Int) {
        (sendAndReceiveCommand(RemoveRequestDTO(id, user)))
    }

    override fun removeLower(veh: Vehicle): Int {
        return (sendAndReceiveCommand(RemoveLowerRequestDTO(veh, user)) as RemoveLowerResponseDTO).cnt
    }

    override fun replaceIfLower(id: Int, element: Vehicle): ReplaceIfLowerResults {
        return (sendAndReceiveCommand(ReplaceIfLowerRequestDTO(id, element, user)) as ReplaceIfLowerResponseDTO).result
    }

    override fun updateVehicleById(newVehicle: Vehicle) {
        sendAndReceiveCommand(UpdateVehicleByIdRequest(id=newVehicle.id, newVehicle, user))
    }

    override fun exit() {
        client.disconnect()
        client.close()
    }
}