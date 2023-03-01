package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.CountByTypeRequestDTO
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.CountByTypeResponseDTO

class CountByTypeCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("count_by_type") {
    override fun handle(requestDTO: RequestDTO): CountByTypeResponseDTO {
        requestDTO as CountByTypeRequestDTO
        try {
            val count = useCase.countByType(requestDTO.vehicleType)
            return CountByTypeResponseDTO(count = count)
        } catch (e: Exception) {
            return CountByTypeResponseDTO(error = e.toString(), count = -1)
        }
    }
}