package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.CountByTypeRequestDTO
import lab6.shared.entities.dtos.commands.CountLessThanEnginePowerRequestDTO
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.CountByTypeResponseDTO
import lab6.shared.entities.dtos.responses.CountLessThanEnginePowerResponseDTO

class CountLessThanEnginePowerCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommandHandler("count_less_than_engine_power") {
    override fun handle(requestDTO: RequestDTO): CountLessThanEnginePowerResponseDTO {
        requestDTO as CountLessThanEnginePowerRequestDTO
        return try {
            val count = useCase.countLessThanEnginePower(requestDTO.enginePower)
            CountLessThanEnginePowerResponseDTO(count = count)
        } catch (e: Exception) {
            CountLessThanEnginePowerResponseDTO(error = e.toString(), count = -1)
        }
    }
}