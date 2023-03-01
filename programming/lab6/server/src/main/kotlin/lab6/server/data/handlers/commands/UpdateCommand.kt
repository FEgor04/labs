package lab6.server.data.handlers.commands

import lab6.server.domain.usecases.CommandsHandlerUseCase
import lab6.shared.entities.dtos.commands.*
import lab6.shared.entities.dtos.responses.*

class UpdateCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommandHandler("update") {
    override fun handle(requestDTO: RequestDTO): UpdateVehicleByIdResponse {
        requestDTO as UpdateVehicleByIdRequest
        return try {
            val results = useCase.update(requestDTO.id, requestDTO.veh)
            UpdateVehicleByIdResponse(error = null)
        } catch (e: Exception) {
            UpdateVehicleByIdResponse(error = e.toString())
        }
    }
}