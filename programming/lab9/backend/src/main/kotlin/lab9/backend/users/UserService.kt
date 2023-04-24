package lab9.backend.users

import lab9.backend.entities.User
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import lab9.backend.exceptions.UserAlreadyExistsException

@Service
interface UserService {
    /**
     * Создает нового пользователя с заданным именем и паролем
     * @return Новый пользователь с заполненным полем id
     * @throws UserAlreadyExistsException если такой пользователь уже существует
     */
    fun createUser(name: String, password: String): User

    /**
     * Проверяет, существует ли пользователь с заданным именем и паролем в базе данных
     * @return Пользователь, соответствующий данному имени и паролю
     * @throws UsernameNotFoundException если такого пользователя нет
     */
    fun authenticateUser(name: String, password: String): User

    /**
     * Возвращает пользователя по его username
     */
    fun getUserByUsername(username: String): User?

    fun getUsersWithLimitAndOffset(pageSize: Int, pageNumber: Int): Page<User>
}