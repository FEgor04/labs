package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.RemoveGreaterRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.*
import lab8.server.domain.usecases.CommandsHandlerUseCase

class RemoveGreaterCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(RemoveGreaterRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): RemoveGreaterResponseDTO {
        requestDTO as RemoveGreaterRequestDTO
        return RemoveGreaterResponseDTO(useCase.removeGreater(requestDTO.vehicle, requestDTO.user), null)
    }
}
