package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.CollectionInfo
import lab6.shared.entities.dtos.commands.*
import lab6.shared.entities.dtos.responses.GetMinByIDResponseDTO
import lab6.shared.entities.dtos.responses.InfoResponseDTO
import lab6.shared.entities.dtos.responses.RemoveGreaterResponseDTO
import lab6.shared.entities.dtos.responses.RemoveLowerResponseDTO
import java.time.LocalDate

class RemoveLowerCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("remove_lower") {
    override fun handle(requestDTO: RequestDTO): RemoveLowerResponseDTO {
        requestDTO as RemoveLowerRequestDTO
        return try {
            val cnt = useCase.removeGreater(requestDTO.veh)
            RemoveLowerResponseDTO(cnt=cnt, error=null)
        } catch(e: Exception) {
            RemoveLowerResponseDTO(error=e.toString(), cnt=0)
        }
    }
}