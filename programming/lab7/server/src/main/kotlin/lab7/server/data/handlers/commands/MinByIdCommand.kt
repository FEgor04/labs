package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.GetMinByIDRequestDTO
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.*
import lab7.server.domain.usecases.CommandsHandlerUseCase

class MinByIdCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(GetMinByIDRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): GetMinByIDResponseDTO {
        requestDTO as GetMinByIDRequestDTO
        return GetMinByIDResponseDTO(useCase.getMinById())
    }
}
