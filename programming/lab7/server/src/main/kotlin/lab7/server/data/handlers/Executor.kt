package lab7.server.data.handlers

import io.prometheus.client.Summary
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import lab7.entities.dtos.requests.AuthRequestDTO
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.*
import lab7.logger.KCoolLogger
import lab7.server.data.handlers.commands.*
import lab7.server.domain.usecases.CommandsHandlerUseCase
import java.net.SocketAddress
import kotlin.reflect.full.primaryConstructor

val handlerCoroutinesPool = Dispatchers.Default

fun CoroutineScope.handleRequest(
    executor: Executor,
    requests: ReceiveChannel<Pair<RequestDTO, SocketAddress>>,
    outputChannel: SendChannel<Pair<ResponseDTO, SocketAddress>>
) = launch(handlerCoroutinesPool) {
    for (req in requests) {
        val response = executor.handle(req.first)
        outputChannel.send(Pair(response, req.second))
    }
}

/**
 * Класс, исполняющий команды
 */
@Suppress("MagicNumber")
class Executor(private val useCase: CommandsHandlerUseCase) {
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
        val response = try {
            command.execute(request)
        } catch (e: Exception) {
            logger.error("Could not execute ${request.name} command. Error: ${e.message}")
            requestTimer.observeDuration()
            return ErrorResponseDTO(request.name, e.message)
        }
        logger.info("Command handled. Returning response")
        requestTimer.observeDuration()
        return response
    }

    suspend fun handleAuth(request: AuthRequestDTO): AuthResponseDTO {
        return AuthCommand(useCase).execute(request)
    }
}
