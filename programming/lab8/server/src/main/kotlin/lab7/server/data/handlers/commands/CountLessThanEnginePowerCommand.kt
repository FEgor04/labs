package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.CountLessThanEnginePowerRequestDTO
import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.responses.*
import lab7.server.domain.usecases.CommandsHandlerUseCase

class CountLessThanEnginePowerCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(CountLessThanEnginePowerRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): CountLessThanEnginePowerResponseDTO {
        requestDTO as CountLessThanEnginePowerRequestDTO
        return CountLessThanEnginePowerResponseDTO(useCase.countLessThanEnginePower(requestDTO.enginePower))
    }
}
