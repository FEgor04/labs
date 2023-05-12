package lab9.backend.adapter.`in`.web.user

import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.adapter.`in`.web.dto.ShowUserResponse
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.user.PermissionDeniedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RequestMapping("/api")
@RestController
class GetMeController(
        private val getUserUseCase: GetUserUseCase,
        private val objectAdapter: WebObjectAdapter,
) {
    @GetMapping("/me")
    fun getMe(p: Principal): ShowUserResponse {
        val user = getUserUseCase.getUserByUsername(p.name) ?: throw PermissionDeniedException()
        return objectAdapter.userToShowUserResponse(user, canYouEdit = true, canYouDelete = true, true, true)
    }
}