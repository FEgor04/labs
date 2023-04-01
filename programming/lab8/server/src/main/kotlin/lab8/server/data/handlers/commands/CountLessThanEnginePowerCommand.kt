package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.CountLessThanEnginePowerRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.*
import lab8.server.domain.usecases.CommandsHandlerUseCase

class CountLessThanEnginePowerCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(CountLessThanEnginePowerRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): CountLessThanEnginePowerResponseDTO {
        requestDTO as CountLessThanEnginePowerRequestDTO
        return CountLessThanEnginePowerResponseDTO(useCase.countLessThanEnginePower(requestDTO.enginePower))
    }
}
