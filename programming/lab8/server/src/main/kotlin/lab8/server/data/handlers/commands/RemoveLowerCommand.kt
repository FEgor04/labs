package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.RemoveLowerRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.*
import lab8.server.domain.usecases.CommandsHandlerUseCase

class RemoveLowerCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(RemoveLowerRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): RemoveLowerResponseDTO {
        requestDTO as RemoveLowerRequestDTO
        return RemoveLowerResponseDTO(useCase.removeLower(requestDTO.veh, requestDTO.user), null)
    }
}
