package lab9.backend.application.port.out.authorities

import lab9.backend.domain.User

interface GetUserGivenAuthoritiesPort {
    fun getUserGivenAuthoritiesToEdit(userID: User.UserID): List<User.UserID>
    fun getUserGivenAuthoritiesToDelete(userID: User.UserID): List<User.UserID>
}