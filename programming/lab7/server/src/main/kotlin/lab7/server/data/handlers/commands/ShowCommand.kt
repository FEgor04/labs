package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.requests.ShowRequestDTO
import lab7.entities.dtos.responses.ShowResponseDTO
import lab7.server.domain.usecases.CommandsHandlerUseCase

class ShowCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommand(ShowRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): ShowResponseDTO {
        requestDTO as ShowRequestDTO
        return ShowResponseDTO(null, useCase.list())
    }
}
