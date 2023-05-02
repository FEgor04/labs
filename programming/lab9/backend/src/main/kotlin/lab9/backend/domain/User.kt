package lab9.backend.domain

data class User private constructor(
    val id: UserID,
    val username: String,
    val password: String
) {
    fun hasID(): Boolean = id != UserID.NoID

    companion object {
        /**
         * Создает нового пользователя без ID.
         * Функция может быть использована для создания пользователя, который еще не сохранен в базе данных
         */
        fun withoutID(username: String, password: String) = User(UserID.NoID, username, password)

        /**
         * Создает пользователя с заданным ID
         * Функция может быть использована для создания пользователя, сохраненного в базе данных
         */
        fun withID(id: UserID, username: String, password: String) = User(id, username, password)
    }

    data class UserID(
        val id: Int
    ) {
        companion object {
            val NoID = UserID(-1)
        }
    }
}
