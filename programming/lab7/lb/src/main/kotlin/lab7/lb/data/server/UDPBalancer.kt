package lab7.lb.data.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lab7.config.SharedConfig
import lab7.entities.balancer.NodeAddress
import lab7.entities.dtos.requests.*
import lab7.entities.dtos.responses.*
import lab7.lb.domain.usecase.BalancerUseCase
import lab7.logger.KCoolLogger
import lab7.udp.BetterDatagramSocket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.nio.charset.Charset

fun CoroutineScope.balanceUDP(
    balancer: UDPBalancer,
    requests: ReceiveChannel<Pair<RequestDTO, InetSocketAddress>>,
    outputChannel: SendChannel<Pair<ResponseDTO, InetSocketAddress>>
) = launch {
    for (req in requests) {
        val response = balancer.balance(req.first, req.second)
        outputChannel.send(Pair(response, req.second))
    }
}

class UDPBalancer(private val usecase: BalancerUseCase) {
    companion object {
        val logger by KCoolLogger()
    }

    suspend fun balance(req: RequestDTO, addr: InetSocketAddress): ResponseDTO {
        return when (req.type) {
            RequestType.MODIFY -> handleModifyRequest(req)
            RequestType.SELECT -> handleSelectRequest(req)
            RequestType.BALANCE -> handleBalanceRequest(req, addr)
        }
    }

    private suspend fun handleModifyRequest(req: RequestDTO): ResponseDTO {
        logger.info("Handling modify request")
        val socket = BetterDatagramSocket(
            withContext(Dispatchers.IO) {
                DatagramSocket(null)
            },
            SharedConfig.ACK_BYTES,
            SharedConfig.CHUNK_SIZE,
            SharedConfig.UDP_FINAL_CHUNK
        )
        val nodeAddress = usecase.getNode().toSocketAddress()
        logger.info("Got node address from balancer. Sending data to node.")
        socket.connect(nodeAddress)
        val data = Json.encodeToString(req).toByteArray(Charset.forName("UTF-8"))
        socket.send(data, nodeAddress)
        val response = socket.receive().first
        logger.info("Received response from node. Sending to client")
        socket.close()
        logger.info("Modify request handled")
        return Json.decodeFromString(response.toString(Charset.forName("UTF-8")))
    }

    private fun handleBalanceRequest(req: RequestDTO, addr: InetSocketAddress): ResponseDTO {
        require(req is RegisterServerRequest)
        return RegisterServerResponse(
            "here you go",
            usecase.addNode(NodeAddress.fromInetSocketAddress(addr))
        )
    }

    private fun handleSelectRequest(req: RequestDTO): ResponseDTO {
        require(req.type == RequestType.SELECT)
        return when(req) {
            is ShowRequestDTO -> {
                ShowResponseDTO(
                    vehicles=usecase.show(),
                    error=null
                )
            }
            is CountByTypeRequestDTO -> {
                CountByTypeResponseDTO(
                    count=usecase.countByType(req.vehicleType),
                )
            }
            is CountLessThanEnginePowerRequestDTO -> {
                CountLessThanEnginePowerResponseDTO(
                    count=usecase.countLessThanEnginePower(req.enginePower),
                )
            }
            is InfoRequestDTO -> {
                InfoResponseDTO(
                    info = usecase.info(),
                )
            }
            is GetMinByIDRequestDTO -> {
                GetMinByIDResponseDTO(
                    min = usecase.getMinByID()
                )
            }
            else -> { TODO() }
        }
    }
}
