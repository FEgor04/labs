package lab9.backend.application.port.`in`.user

import lab9.backend.application.port.`in`.vehicles.PagedResponse
import lab9.backend.common.UseCase
import lab9.backend.domain.User

@UseCase
interface GetUserUseCase {
    fun getUserByUsername(username: String): User?
    fun getUserById(id: User.UserID): User?
    fun getUsers(pageNumber: Int, pageSize: Int): PagedResponse<User>
}