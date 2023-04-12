package lab8.server.data.handlers

import io.prometheus.client.Summary
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import lab8.entities.dtos.requests.AuthRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.requests.SignUpRequest
import lab8.entities.dtos.responses.*
import lab8.logger.KCoolLogger
import lab8.server.data.handlers.commands.*
import lab8.server.domain.usecases.CommandsHandlerUseCase
import lab8.udp.server.RequestsHandler
import java.net.SocketAddress
import kotlin.reflect.full.primaryConstructor

val handlerCoroutinesPool = Dispatchers.Default

fun CoroutineScope.handleRequest(
    executor: Executor,
    requests: ReceiveChannel<Pair<RequestDTO, SocketAddress>>,
    outputChannel: SendChannel<Pair<ResponseDTO, SocketAddress>>
) = launch(handlerCoroutinesPool) {
    val logger by KCoolLogger()

    for (req in requests) {
        val response: ResponseDTO = try {
            executor.handle(req.first)
        } catch (e: Exception) {
            logger.error("Could not handle ${req.first.name} command from ${req.second}. Error: $e")
            ErrorResponseDTO(
                name = req.first.name,
                error = e.toString()
            )
        }
        outputChannel.send(Pair(response, req.second))
    }
}

/**
 * Класс, исполняющий команды
 */
@Suppress("MagicNumber")
class Executor(private val useCase: CommandsHandlerUseCase) : RequestsHandler<RequestDTO, ResponseDTO> {
    companion object {
        private val logger by KCoolLogger()
        private val requestLatency: Summary =
            Summary.build()
                .name("executor_requests_tth_seconds")
                .help("Time to handle request in seconds.")
                .quantile(0.5, 0.05)
                .quantile(0.9, 0.01)
                .quantile(0.99, 0.001)
                .labelNames("command")
                .register()
    }

    private val commands: List<Command> = DefaultCommand::class.sealedSubclasses.map {
        it.primaryConstructor!!.call(useCase)
    }

    @Suppress("ReturnCount", "TooGenericExceptionCaught")
    suspend fun handle(request: RequestDTO): ResponseDTO {
        val requestTimer = requestLatency.labels(request.name).startTimer()
        if(request is SignUpRequest) {
            val response = handleSignUp(request)
            requestTimer.observeDuration()
            return response
        }
        if (request is AuthRequestDTO) {
            val response = handleAuth(request)
            requestTimer.observeDuration()
            return response
        }
        if (useCase.authUser(request.user) <= 0) {
            requestTimer.observeDuration()
            return BadCredentialsResponseDTO("no such user or password is wrong")
        }
        logger.info("Handling $request. User: ${request.user}")
        val command = commands.find { it.check(request) } ?: return NoSuchCommandResponseDTO(request.name).also {
            logger.warn("Could not find command ${request.name}").also {
                requestTimer.observeDuration()
            }
        }
        logger.info("Needed command found. Executing!")
        val response = command.execute(request)
        logger.info("Command handled. Returning response")
        requestTimer.observeDuration()
        return response
    }

    private suspend fun handleSignUp(request: SignUpRequest): SignUpResponse {
        val newId = useCase.createUser(request.user.name, request.user.password)
        return SignUpResponse(request.user.copy(id = newId), error = null)
    }

    private suspend fun handleAuth(request: AuthRequestDTO): AuthResponseDTO {
        return AuthCommand(useCase).execute(request)
    }

    override suspend fun handle(request: RequestDTO, addr: SocketAddress): Pair<ResponseDTO, SocketAddress> {
        return Pair(this.handle(request), addr)
    }
}
