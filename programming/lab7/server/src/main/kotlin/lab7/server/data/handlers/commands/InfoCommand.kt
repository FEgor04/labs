package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.InfoRequestDTO
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.*
import lab7.server.domain.usecases.CommandsHandlerUseCase

class InfoCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(InfoRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): InfoResponseDTO {
        requestDTO as InfoRequestDTO
        return InfoResponseDTO(useCase.collectionInfo)
    }
}
