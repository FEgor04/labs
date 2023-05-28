package lab8.server.domain.auth

interface AuthManager {
    /**
     * Создает нового пользователя
     */
    suspend fun createUser(login: String, password: String): Int

    /**
     * Проверяет, существует ли пользователь с данным логином и паролем в базе данных.
     * @return ID пользователя, если такой пользователь существует и пароль верный
     * и 0, если пользователя не существует или был дан неверный пароль
     */
    suspend fun authUser(login: String, password: String): Int
}
