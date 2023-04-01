package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.ReplaceIfLowerRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.*
import lab8.server.domain.usecases.CommandsHandlerUseCase

class ReplaceIfLowerCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(ReplaceIfLowerRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): ReplaceIfLowerResponseDTO {
        requestDTO as ReplaceIfLowerRequestDTO
        return ReplaceIfLowerResponseDTO(useCase.replaceIfLower(requestDTO.id, requestDTO.veh, requestDTO.user), null)
    }
}
