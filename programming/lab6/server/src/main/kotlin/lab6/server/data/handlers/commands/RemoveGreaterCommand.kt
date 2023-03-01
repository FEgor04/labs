package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.RemoveGreaterRequestDTO
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.RemoveGreaterResponseDTO

class RemoveGreaterCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("remove_greater") {
    override fun handle(requestDTO: RequestDTO): RemoveGreaterResponseDTO {
        requestDTO as RemoveGreaterRequestDTO
        return try {
            val cnt = useCase.removeGreater(requestDTO.vehicle)
            RemoveGreaterResponseDTO(cnt=cnt, error=null)
        } catch(e: Exception) {
            RemoveGreaterResponseDTO(error=e.toString(), cnt=0)
        }
    }
}