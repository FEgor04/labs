package lab9.backend.adapter.`in`.web.user

import lab9.backend.application.port.`in`.signup.SignUpCommand
import lab9.backend.application.port.`in`.signup.SignUpUseCase
import lab9.backend.application.port.`in`.signup.UserAlreadyExistsException
import lab9.backend.domain.User
import lab9.backend.logger.KCoolLogger
import lab9.common.requests.SignUpRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SignUpController(
    private val signUpUseCase: SignUpUseCase,
) {
    private val logger by KCoolLogger()

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<User> {
        logger.info("Handling /api/signup request for username ${request.username}")
        return try {
            val newUser = signUpUseCase.signUp(SignUpCommand(request.username, request.password))
            logger.info("Created new user ${newUser}")
            ResponseEntity.ok(newUser)
        } catch (e: UserAlreadyExistsException) {
            return ResponseEntity.badRequest().build()
        }
    }
}