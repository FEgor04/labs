package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.ClearRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.ClearResponseDTO
import lab8.server.domain.usecases.CommandsHandlerUseCase

class ClearCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommand(ClearRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): ClearResponseDTO {
        requestDTO as ClearRequestDTO
        useCase.clear(requestDTO.user)
        return ClearResponseDTO(null)
    }
}
