package lab9.backend.application.service

import lab9.backend.application.port.`in`.user.GetUserUseCase
import lab9.backend.application.port.`in`.vehicles.PagedResponse
import lab9.backend.application.port.out.user.LoadUserPort
import lab9.backend.domain.User
import org.springframework.stereotype.Service

@Service
class GetUserService(
    private val loadUserPort: LoadUserPort
): GetUserUseCase {
    override fun getUserByUsername(username: String): User? {
        return loadUserPort.loadUserByUsername(username)
    }

    override fun getUserById(id: User.UserID): User? {
        return loadUserPort.loadUserById(id)
    }

    override fun getUsers(pageNumber: Int, pageSize: Int): PagedResponse<User> {
        return loadUserPort.getUsers(pageNumber, pageSize)
    }

}