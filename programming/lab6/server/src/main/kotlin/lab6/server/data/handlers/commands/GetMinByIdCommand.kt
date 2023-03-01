package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.CollectionInfo
import lab6.shared.entities.dtos.commands.CountLessThanEnginePowerRequestDTO
import lab6.shared.entities.dtos.commands.GetMinByIDRequestDTO
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.GetMinByIDResponseDTO
import lab6.shared.entities.dtos.responses.InfoResponseDTO
import java.time.LocalDate

class GetMinByIdCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("min_by_id") {
    override fun handle(requestDTO: RequestDTO): GetMinByIDResponseDTO {
        requestDTO as GetMinByIDRequestDTO
        return try {
            val min = useCase.getMinById()
            GetMinByIDResponseDTO(min)
        } catch(e: Exception) {
            GetMinByIDResponseDTO(error=e.toString(), min=null)
        }
    }
}