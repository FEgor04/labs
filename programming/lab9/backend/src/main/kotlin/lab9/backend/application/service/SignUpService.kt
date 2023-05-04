package lab9.backend.application.service

import lab9.backend.application.port.`in`.signup.SignUpCommand
import lab9.backend.application.port.`in`.signup.SignUpUseCase
import lab9.backend.application.port.out.user.CreateUserPort
import lab9.backend.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignUpService(
    private val createUserPort: CreateUserPort,
    private val passwordEncoder: PasswordEncoder,
) : SignUpUseCase {
    override fun signUp(command: SignUpCommand): User {
        return createUserPort.createUser(User.withoutID(command.username, passwordEncoder.encode(command.password)))
    }
}