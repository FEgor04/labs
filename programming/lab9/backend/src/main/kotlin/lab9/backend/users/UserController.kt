package lab9.backend.users

import lab9.backend.entities.User
import lab9.backend.exceptions.UserAlreadyExistsException
import lab9.backend.logger.KCoolLogger
import lab9.common.dto.UserDTO
import lab9.common.requests.CreateUserRequest
import lab9.common.responses.ShowUserResponse
import lab9.common.responses.ShowUsersResponse
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {
    companion object {
        private val logger by KCoolLogger()
    }

    @PostMapping("/signup")
    fun createUser(@RequestBody user: CreateUserRequest): ResponseEntity<ShowUserResponse> {
        logger.info("Handling POST request at /signup. Username: ${user.username}")
        try {
            val newUser = userService.createUser(user.username, user.password)
            logger.info("Successfully created new user at id ${newUser.id}")
            return ResponseEntity.ok(newUser.toShowUserResponse())
        } catch (e: UserAlreadyExistsException) {
            logger.error("Could not create user: $e")
            throw UserAlreadyExistsException()
        }
    }

    @GetMapping("/users")
    fun getUsers(
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "0") pageNumber: Int
    ): ShowUsersResponse {
        val page = userService.getUsersWithLimitAndOffset(pageSize, pageNumber)
        return ShowUsersResponse(
            page.content.map { it.toShowUserResponse() }.toTypedArray(),
            page.totalPages,
            page.totalElements.toInt()
        )
    }

    @GetMapping("/me")
    fun getMe(principal: Principal): ResponseEntity<UserDTO> {
        val user = userService.getUserByUsername(principal.name) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(user.toDTO())
    }

}