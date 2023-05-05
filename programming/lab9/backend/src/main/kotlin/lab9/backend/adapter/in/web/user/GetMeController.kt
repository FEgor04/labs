package lab9.backend.adapter.`in`.web.user

import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.common.responses.ShowUserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RequestMapping("/api")
@RestController
class GetMeController(
    private val getUserUseCase: GetUserUseCase
) {
    @GetMapping("/me")
    fun getMe(p: Principal): ResponseEntity<ShowUserResponse> {
        val user = getUserUseCase.getUserByUsername(p.name) ?: return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(
            ShowUserResponse(
                user.id.id,
                user.username
            )
        )
    }
}