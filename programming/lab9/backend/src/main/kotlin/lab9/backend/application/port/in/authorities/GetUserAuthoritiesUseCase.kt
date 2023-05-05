package lab9.backend.application.port.`in`.authorities

import lab9.backend.domain.User

interface GetUserAuthoritiesUseCase {
    fun getUserAuthoritiesToDelete(userId: User.UserID): List<User.UserID>

    fun getUserAuthoritiesToEdit(userId: User.UserID): List<User.UserID>
}