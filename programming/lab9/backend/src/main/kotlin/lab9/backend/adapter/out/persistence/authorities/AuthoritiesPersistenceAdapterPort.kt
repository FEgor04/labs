package lab9.backend.adapter.out.persistence.authorities

import lab9.backend.adapter.out.persistence.user.UserJpaEntity
import lab9.backend.adapter.out.persistence.user.UserRepository
import lab9.backend.application.port.`in`.user.UserDoesNotExistsException
import lab9.backend.application.port.out.authorities.*
import lab9.backend.domain.User
import lab9.backend.logger.KCoolLogger
import org.springframework.integration.jpa.dsl.Jpa
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class AuthoritiesPersistenceAdapterPort(
    private val authoritiesRepository: AuthoritiesRepository,
    private val userRepository: UserRepository,
) : GetUserAuthoritiesToDeletePort, GetUserAuthoritiesToEditPort, SetUserAuthoritiesPort {
    private val logger by KCoolLogger()

    override fun getUserAuthoritiesToDelete(userID: User.UserID): List<User.UserID> {
        printAll()
        logger.info("Finding all user's that has given delete authority to User#${userID.id}")
        return authoritiesRepository
            .getAllByAuthorizedToIdAndCanDeleteIs(userID.id, true)
            .map { User.UserID(it.owner.id!!) }
    }

    fun printAll() {
        logger.info("Printing all entries in authorties repository")
        authoritiesRepository.findAll().forEach {
            logger.info("$it")
        }
    }

    override fun getUserAuthoritiesToEdit(userID: User.UserID): List<User.UserID> {
        return authoritiesRepository
            .getAllByAuthorizedToIdAndCanEditIs(userID.id, true)
            .map { User.UserID(it.owner.id!!) }
    }

    override fun setUserAuthoritiesToDelete(
        userID: User.UserID,
        ownerId: User.UserID,
        canDelete: Boolean,
        canEdit: Boolean
    ) {

        val owner = userRepository.findFirstById(ownerId.id)
        val user = userRepository.findFirstById(userID.id)
        if (owner == null || user == null) {
            throw UserDoesNotExistsException()
        }
        val previousAuthority = authoritiesRepository.findFirstByAuthorizedToIdIsAndOwnerIdIs(userID.id, ownerId.id)
        if (previousAuthority == null) {
            authoritiesRepository.save(
                AuthorityJpaEntity(
                    id = AuthorityEntityPK(ownerId = ownerId.id, authorizedId = userID.id),
                    owner = owner,
                    authorizedTo = user,
                    canDelete = canDelete,
                    canEdit = canEdit,
                )
            )
        } else {
            authoritiesRepository.save(
                previousAuthority.copy(canDelete = canDelete, canEdit = canEdit)
            )
        }

    }
}