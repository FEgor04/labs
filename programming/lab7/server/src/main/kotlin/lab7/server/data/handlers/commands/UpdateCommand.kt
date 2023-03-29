package lab7.server.data.handlers.commands

import lab7.entities.dtos.requests.RequestDTO
import lab7.entities.dtos.requests.UpdateVehicleByIdRequestDTO
import lab7.entities.dtos.responses.*
import lab7.server.domain.usecases.CommandsHandlerUseCase

class UpdateCommand(private val useCase: CommandsHandlerUseCase) :
    DefaultCommand(UpdateVehicleByIdRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): UpdateVehicleByIdResponse {
        requestDTO as UpdateVehicleByIdRequestDTO
        useCase.update(requestDTO.id, requestDTO.veh, requestDTO.user)
        return UpdateVehicleByIdResponse(null)
    }
}
