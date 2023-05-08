package lab9.backend.adapter.`in`.web.user

import lab9.backend.application.port.`in`.authorities.GrantAuthoritiesUseCase
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.domain.BadAuthenticationException
import lab9.backend.domain.User
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/users")
class GrantAuthoritiesController(
    private val grantAuthoritiesUseCase: GrantAuthoritiesUseCase,
    private val getUserUseCase: GetUserUseCase,
) {
    @PutMapping("/grant/{to}")
    fun grantAuthority(
        @PathVariable to: Int,
        @RequestParam(required = true, defaultValue = "false") canDelete: Boolean,
        @RequestParam(required=true, defaultValue = "false") canEdit: Boolean,
        p: Principal
    ) {
        val owner = getUserUseCase.getUserByUsername(p.name) ?: throw BadAuthenticationException()
        grantAuthoritiesUseCase.grantAuthorities(owner.id, User.UserID(to), canEdit = canEdit, canDelete = canDelete)
    }
}