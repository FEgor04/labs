package lab8.server.data.handlers.commands

import lab8.entities.dtos.requests.AuthRequestDTO
import lab8.entities.dtos.requests.RequestDTO
import lab8.entities.dtos.responses.AuthResponseDTO
import lab8.server.domain.usecases.CommandsHandlerUseCase

class AuthCommand(private val useCase: CommandsHandlerUseCase) : DefaultCommand(AuthRequestDTO::class) {

    override suspend fun execute(requestDTO: RequestDTO): AuthResponseDTO {
//        return ShowResponseDTO(null, emptyList())
        val id = useCase.authUser(requestDTO.user)
        if (id <= 0) {
            return AuthResponseDTO(requestDTO.user, "no such user")
        } else {
            return AuthResponseDTO(requestDTO.user.copy(id = id), null)
        }
    }
}