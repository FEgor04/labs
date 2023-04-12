package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.InfoRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.*
import lab8.server.domain.usecases.CommandsHandlerUseCase

class InfoCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(InfoRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): InfoResponseDTO {
        requestDTO as InfoRequestDTO
        return InfoResponseDTO(useCase.collectionInfo)
    }
}
