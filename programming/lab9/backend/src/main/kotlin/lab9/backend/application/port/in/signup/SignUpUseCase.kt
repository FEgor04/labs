package lab9.backend.application.port.`in`.signup

import lab9.backend.common.UseCase
import lab9.backend.domain.User

@UseCase
interface SignUpUseCase {
    /**
     * Регистрирует нового пользователя
     * @throws UserAlreadyExistsException если такой пользователь уже существует
     */
    fun signUp(command: SignUpCommand): User
}