package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.ClearRequestDTO
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.responses.ClearResponseDTO

class ClearHandler (private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("clear"){
    override fun handle(requestDTO: RequestDTO): ClearResponseDTO {
        if(requestDTO is ClearRequestDTO) {
            try {
                useCase.clear()
            }
            catch (e: Exception) {
                return ClearResponseDTO(e.toString())
            }
            return ClearResponseDTO(null)
        }
        else {
            throw IllegalArgumentException("request is not $name command")
        }
    }
}