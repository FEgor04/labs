package lab9.backend.adapter.out.persistence.authorities

import lab9.backend.application.port.out.authorities.GetUserAuthoritiesToDeletePort
import lab9.backend.application.port.out.authorities.GetUserAuthoritiesToEditPort
import lab9.backend.domain.User
import org.springframework.stereotype.Component

@Component
class AuthoritiesPersistenceAdapterPort(
    private val authoritiesRepository: AuthoritiesRepository,
) : GetUserAuthoritiesToDeletePort, GetUserAuthoritiesToEditPort {
    override fun getUserAuthoritiesToDelete(userID: User.UserID): List<User.UserID> {
        return authoritiesRepository
            .getAllByAuthorizedToIdAndCanDeleteIs(userID.id, true)
            .map { User.UserID(it.owner.id!!) }
    }

    override fun getUserAuthoritiesToEdit(userID: User.UserID): List<User.UserID> {
        return authoritiesRepository
            .getAllByAuthorizedToIdAndCanEditIs(userID.id, true)
            .map { User.UserID(it.owner.id!!) }
    }
}