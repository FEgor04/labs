package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.AddRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.AddResponseDTO
import lab8.server.domain.usecases.CommandsHandlerUseCase

class AddCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommand(AddRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): AddResponseDTO {
        requestDTO as AddRequestDTO
        val newId = useCase.add(requestDTO.vehicle, requestDTO.user)
        return AddResponseDTO(null, newId)
    }
}
