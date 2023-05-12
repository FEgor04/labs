package lab9.backend.application.port.out.user

import lab9.backend.application.port.`in`.vehicles.PagedResponse
import lab9.backend.domain.User

interface LoadUserPort {
    fun loadUserByUsername(username: String): User?
    fun loadUserById(id: User.UserID): User?
    fun getUsers(pageNumber: Int, pageSize: Int): PagedResponse<User>
}