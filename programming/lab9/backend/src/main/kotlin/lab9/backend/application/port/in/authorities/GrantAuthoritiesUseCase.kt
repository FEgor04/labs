package lab9.backend.application.port.`in`.authorities

import lab9.backend.common.UseCase
import lab9.backend.domain.User

@UseCase
interface GrantAuthoritiesUseCase {
    fun grantAuthorities(from: User.UserID, to: User.UserID, canEdit: Boolean, canDelete: Boolean)
}