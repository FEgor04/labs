package lab9.backend.application.port.`in`.authorities

import lab9.backend.application.port.`in`.user.UserDoesNotExistsException
import lab9.backend.common.UseCase
import lab9.backend.domain.User

@UseCase
interface GrantAuthoritiesUseCase {
    /**
     * Дает заданные права пользователю to на редакторивание / удаление транспорта пользователя from
     * @throws UserDoesNotExistsException если такого пользователя не существует
     */
    @Throws(UserDoesNotExistsException::class)
    fun grantAuthorities(from: User.UserID, to: User.UserID, canEdit: Boolean, canDelete: Boolean)
}