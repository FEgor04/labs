package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.RemoveGreaterRequestDTO
import lab6.shared.entities.dtos.commands.RemoveRequestDTO
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.RemoveGreaterResponseDTO
import lab6.shared.entities.dtos.responses.RemoveResponseDTO

class RemoveCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("remove_key") {
    override fun handle(requestDTO: RequestDTO): RemoveResponseDTO {
        requestDTO as RemoveRequestDTO
        return try {
            useCase.remove(requestDTO.id)
            RemoveResponseDTO(error=null)
        } catch(e: Exception) {
            RemoveResponseDTO(error=e.toString())
        }
    }
}