package lab9.backend.application.port.`in`.authorities

import lab9.backend.domain.User
import lab9.backend.domain.Vehicle

interface GetUserAuthoritiesUseCase {
    /**
     * Возвращает список пользователей, которые дали данному пользователю
     * право удалять их транспорт
     */
    fun getUserAuthoritiesToDelete(userId: User.UserID): List<User.UserID>
    /**
     * Возвращает список пользователей, которые дали данному пользователю
     * право редактировать их транспорт
     */
    fun getUserAuthoritiesToEdit(userId: User.UserID): List<User.UserID>
    /**
     * Возвращает список пользователей, которым данный пользователь дал
     * право редактировать свой транспорт
     */
    fun getUserGivenAuthoritiesToEdit(userId: User.UserID): List<User.UserID>
    /**
     * Возвращает список пользователей, которым данный пользователь дал
     * право удалять свой транспорт
     */
    fun getUserGivenAuthoritiesToDelete(userId: User.UserID): List<User.UserID>

}