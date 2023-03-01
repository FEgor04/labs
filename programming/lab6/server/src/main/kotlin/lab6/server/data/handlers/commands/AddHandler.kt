package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.AddRequestDTO
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.AddResponseDTO

class AddHandler(private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("add") {
    override fun handle(requestDTO: RequestDTO): AddResponseDTO {
        requestDTO as AddRequestDTO
        val newId: Int?
        try {
            newId = this.useCase.add(requestDTO.vehicle)
        } catch (e: Exception) {
            return AddResponseDTO(e.toString(), -1)
        }
        return AddResponseDTO(null, newId)
    }
}