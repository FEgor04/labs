package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.CountByTypeRequestDTO
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.*
import lab7.server.domain.usecases.CommandsHandlerUseCase

class CountByTypeCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommand(CountByTypeRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): CountByTypeResponseDTO {
        requestDTO as CountByTypeRequestDTO
        return CountByTypeResponseDTO(useCase.countByType(requestDTO.vehicleType))
    }
}
