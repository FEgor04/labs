package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.*
import lab6.shared.entities.dtos.responses.*

class ReplaceIfLowerCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("replace_if_lower") {
    override fun handle(requestDTO: RequestDTO): ReplaceIfLowerResponseDTO {
        requestDTO as ReplaceIfLowerRequestDTO
        return try {
            val results = useCase.replaceIfLower(requestDTO.id, requestDTO.veh)
            ReplaceIfLowerResponseDTO(results, error=null)
        } catch(e: Exception) {
            ReplaceIfLowerResponseDTO(error=e.toString(), result = ReplaceIfLowerResults.NOT_REPLACED)
        }
    }
}