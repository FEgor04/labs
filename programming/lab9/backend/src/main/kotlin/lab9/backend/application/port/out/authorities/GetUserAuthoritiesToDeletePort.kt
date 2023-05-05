package lab9.backend.application.port.out.authorities

import lab9.backend.domain.User

interface GetUserAuthoritiesToDeletePort {
    /**
     * Возвращает список пользователей, чьи машины данный пользователь может удалять
     */
    fun getUserAuthoritiesToDelete(userID: User.UserID): List<User.UserID>
}