package lab7.server.data.handlers.commands

import kotlinx.coroutines.delay
import lab7.entities.dtos.requests.DelayRequestDTO
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.DelayResponse
import lab7.logger.KCoolLogger
import lab7.server.domain.usecases.CommandsHandlerUseCase

@Suppress("UnusedPrivateMember")
class DelayCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommand(DelayRequestDTO::class) {
    private val logger by KCoolLogger()

    override suspend fun execute(requestDTO: RequestDTO): DelayResponse {
        logger.info("Sleeping!")
        delay(delayTime)
        logger.info("Slept!")
        return DelayResponse(null)
    }

    companion object {
        private const val delayTime: Long = 5000
    }
}