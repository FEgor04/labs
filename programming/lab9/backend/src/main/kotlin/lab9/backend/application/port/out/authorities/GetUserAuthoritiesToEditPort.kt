package lab9.backend.application.port.out.authorities

import lab9.backend.domain.User

interface GetUserAuthoritiesToEditPort {
    /**
     * Возвращает список пользователей, чьи машины данный пользователь может изменять
     */
    fun getUserAuthoritiesToEdit(userID: User.UserID): List<User.UserID>
}