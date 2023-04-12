package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.requests.UpdateVehicleByIdRequestDTO
import lab8.entities.dtos.responses.*
import lab8.server.domain.usecases.CommandsHandlerUseCase

class UpdateCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(UpdateVehicleByIdRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): UpdateVehicleByIdResponse {
        requestDTO as UpdateVehicleByIdRequestDTO
        try {
            useCase.update(requestDTO.id, requestDTO.veh, requestDTO.user)
        }
        catch(e: Exception) {
            return UpdateVehicleByIdResponse(e.toString())
        }
        return UpdateVehicleByIdResponse(null)
    }
}
