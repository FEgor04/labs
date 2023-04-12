package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.RemoveRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.*
import lab8.server.domain.usecases.CommandsHandlerUseCase

class RemoveCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(RemoveRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): RemoveResponseDTO {
        requestDTO as RemoveRequestDTO
        useCase.remove(requestDTO.id, requestDTO.user)
        return RemoveResponseDTO(null)
    }
}
