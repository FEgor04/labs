package lab9.backend.domain

data class User private constructor(
    val id: UserID,
    val username: String,
    val password: String,
    val authorizedToEditBy: List<UserID>,
    val authorizedToDeleteBy: List<UserID>,
    val hasGivenAuthorizationToEditTo: List<UserID>,
    val hasGivenAuthorizationToDeleteTo: List<UserID>,
) {
    fun hasID(): Boolean = id != UserID.NoID

    companion object {
        /**
         * Создает нового пользователя без ID.
         * Функция может быть использована для создания пользователя, который еще не сохранен в базе данных
         */
        fun withoutID(
            username: String,
            password: String,
            authorizedToEditBy: List<UserID>,
            authorizedToDeleteBy: List<UserID>,
            hasGivenAuthorizationToEditTo: List<UserID>,
            hasGivenAuthorizationToDeleteTo: List<UserID>,
        ) = User(
            UserID.NoID,
            username,
            password,
            authorizedToEditBy,
            authorizedToDeleteBy,
            hasGivenAuthorizationToEditTo,
            hasGivenAuthorizationToDeleteTo
        )

        /**
         * Создает пользователя с заданным ID
         * Функция может быть использована для создания пользователя, сохраненного в базе данных
         */
        fun withID(
            id: UserID,
            username: String,
            password: String,
            authorizedToEditBy: List<UserID>,
            authorizedToDeleteBy: List<UserID>,
            hasGivenAuthorizationToEditTo: List<UserID>,
            hasGivenAuthorizationToDeleteTo: List<UserID>,
        ) = User(
            id,
            username,
            password,
            authorizedToEditBy,
            authorizedToDeleteBy,
            hasGivenAuthorizationToEditTo,
            hasGivenAuthorizationToDeleteTo
        )
    }

    data class UserID(
        val id: Int
    ) {
        companion object {
            val NoID = UserID(-1)
        }
    }
}
