package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.RemoveRequestDTO
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.*
import lab7.server.domain.usecases.CommandsHandlerUseCase

class RemoveCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(RemoveRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): RemoveResponseDTO {
        requestDTO as RemoveRequestDTO
        useCase.remove(requestDTO.id, requestDTO.user)
        return RemoveResponseDTO(null)
    }
}
