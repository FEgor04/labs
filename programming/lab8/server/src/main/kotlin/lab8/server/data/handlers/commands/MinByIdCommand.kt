package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.GetMinByIDRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.*
import lab8.server.domain.usecases.CommandsHandlerUseCase

class MinByIdCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(GetMinByIDRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): GetMinByIDResponseDTO {
        requestDTO as GetMinByIDRequestDTO
        return GetMinByIDResponseDTO(useCase.getMinById())
    }
}
