package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.RequestDTO
import lab6.shared.entities.dtos.commands.ShowRequestDTO
import lab6.shared.entities.dtos.responses.ShowResponseDTO
import lab6.shared.entities.vehicle.Vehicle

class ShowHandler (private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("show"){

    override fun handle(requestDTO: RequestDTO): ShowResponseDTO {
        if(requestDTO is ShowRequestDTO) {
            val list: List<Vehicle>
            try {
                list = useCase.list()
            }
            catch (e: Exception) {
                return ShowResponseDTO(e.toString(), arrayListOf())
            }
            return ShowResponseDTO(null, list)
        }
        else {
            throw IllegalArgumentException("request is not show command")
        }
    }
}