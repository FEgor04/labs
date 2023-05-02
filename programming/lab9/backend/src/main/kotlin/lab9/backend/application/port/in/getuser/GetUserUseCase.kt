package lab9.backend.application.port.`in`.getuser

import lab9.backend.domain.User

interface GetUserUseCase {
    fun getUserByUsername(username: String): User?
    fun getUserById(id: User.UserID): User?
}