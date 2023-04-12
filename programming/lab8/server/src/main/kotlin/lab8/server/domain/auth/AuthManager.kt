package lab8.server.domain.auth
import lab8.exceptions.ServerException.UserAlreadyExistsException

interface AuthManager {
    /**
     * Создает нового пользователя
     * @throws ServerException.UserAlreadyExistsException если пользователь с таким имененем уже существует
     */
    suspend fun createUser(login: String, password: String): Int

    /**
     * Проверяет, существует ли пользователь с данным логином и паролем в базе данных.
     * @return ID пользователя, если такой пользователь существует и пароль верный
     * и 0, если пользователя не существует или был дан неверный пароль
     */
    suspend fun authUser(login: String, password: String): Int
}
