package lab9.backend.adapter.`in`.web.user

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import lab9.backend.adapter.`in`.web.WebObjectAdapter
import lab9.backend.adapter.`in`.web.dto.GetUsersResponse
import lab9.backend.application.port.`in`.authorities.CheckAuthoritiesUseCase
import lab9.backend.application.port.`in`.authorities.GetUserAuthoritiesUseCase
import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.user.PermissionDeniedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/users")
class GetUsersController(
        private val getUserUseCase: GetUserUseCase,
        private val objectAdapter: WebObjectAdapter,
        val getUserAuthoritiesUseCase: GetUserAuthoritiesUseCase,
) {
    @GetMapping("")
    fun getUsers(
            @RequestParam(defaultValue = "0")
            @Min(0)
            pageNumber: Int,
            @RequestParam(defaultValue = "10")
            @Min(1)
            @Max(50)
            pageSize: Int,
            principal: Principal,
    ): GetUsersResponse {
        val user = getUserUseCase.getUserByUsername(principal.name) ?: throw PermissionDeniedException()
        val users = getUserUseCase.getUsers(pageNumber, pageSize)
        val usersAuthoritiesToDelete = getUserAuthoritiesUseCase.getUserAuthoritiesToDelete(user.id).toHashSet()
        val usersAuthoritiesToEdit = getUserAuthoritiesUseCase.getUserAuthoritiesToEdit(user.id).toHashSet()
        val usersGivenAuthoritiesToDelete = getUserAuthoritiesUseCase.getUserGivenAuthoritiesToDelete(user.id).toHashSet()
        val usersGivenAuthoritiesToEdit = getUserAuthoritiesUseCase.getUserGivenAuthoritiesToEdit(user.id).toHashSet()
        return objectAdapter.usersToResponse(
                users,
                canYouDeleteResolver = {
                    it in usersAuthoritiesToDelete
                },
                canYouEditResolver = {
                    it in usersAuthoritiesToEdit
                },
                canHeDeleteResolver = {
                    it in usersGivenAuthoritiesToDelete
                },
                canHeEditResolver = {
                    it in usersGivenAuthoritiesToEdit
                }
        )
    }
}