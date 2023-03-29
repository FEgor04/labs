package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.RemoveGreaterRequestDTO
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.*
import lab7.server.domain.usecases.CommandsHandlerUseCase

class RemoveGreaterCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(RemoveGreaterRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): RemoveGreaterResponseDTO {
        requestDTO as RemoveGreaterRequestDTO
        return RemoveGreaterResponseDTO(useCase.removeGreater(requestDTO.vehicle, requestDTO.user), null)
    }
}
