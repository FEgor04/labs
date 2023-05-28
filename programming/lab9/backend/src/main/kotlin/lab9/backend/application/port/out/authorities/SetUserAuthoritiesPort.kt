package lab9.backend.application.port.out.authorities

import lab9.backend.domain.User

@FunctionalInterface
interface SetUserAuthoritiesPort {
    fun setUserAuthoritiesToDelete(userID: User.UserID, ownerId: User.UserID, canDelete: Boolean, canEdit: Boolean)
}