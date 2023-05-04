package lab9.backend.application.port.out.user

import lab9.backend.application.port.`in`.signup.UserAlreadyExistsException
import lab9.backend.domain.User
import kotlin.jvm.Throws

interface CreateUserPort {
    /**
     * Сохраняет нового пользовтеля в базу данных
     * @throws UserAlreadyExistsException если такой пользователь уже существует
     */
    @Throws(UserAlreadyExistsException::class)
    fun createUser(user: User): User
}