package lab9.backend.adapter.`in`.web.user

import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.adapter.`in`.web.dto.ShowUserResponse
import lab9.backend.adapter.`in`.web.dto.SignUpRequest
import lab9.backend.application.port.`in`.signup.SignUpCommand
import lab9.backend.application.port.`in`.signup.SignUpUseCase
import lab9.backend.logger.KCoolLogger
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SignUpController(
        private val signUpUseCase: SignUpUseCase,
        private val objectAdapter: WebObjectAdapter
) {
    private val logger by KCoolLogger()

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ShowUserResponse {
        logger.info("Handling /api/signup request for username ${request.username}")
        val newUser = signUpUseCase.signUp(SignUpCommand(request.username, request.password))
        logger.info("Created new user ${newUser}")
        return objectAdapter.userToShowUserResponse(newUser, canYouEdit = true, canYouDelete = true, true, true)
    }
}