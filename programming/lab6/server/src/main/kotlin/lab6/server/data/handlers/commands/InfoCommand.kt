package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.CollectionInfo
import lab6.shared.entities.dtos.commands.CountLessThanEnginePowerRequestDTO
import lab6.shared.entities.dtos.commands.InfoRequestDTO
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.InfoResponseDTO
import java.time.LocalDate

class InfoCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("info") {
    override fun handle(requestDTO: RequestDTO): InfoResponseDTO {
        requestDTO as InfoRequestDTO
        try {
            val collectionInfo = useCase.collectionInfo
            return InfoResponseDTO(info = collectionInfo)
        } catch (e: Exception) {
            return InfoResponseDTO(error = e.toString(), info = CollectionInfo(-1, LocalDate.MIN, ""))
        }
    }
}