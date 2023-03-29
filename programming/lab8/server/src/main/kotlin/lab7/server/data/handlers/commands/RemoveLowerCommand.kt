package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.RemoveLowerRequestDTO
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.*
import lab7.server.domain.usecases.CommandsHandlerUseCase

class RemoveLowerCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(RemoveLowerRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): RemoveLowerResponseDTO {
        requestDTO as RemoveLowerRequestDTO
        return RemoveLowerResponseDTO(useCase.removeLower(requestDTO.veh, requestDTO.user), null)
    }
}
