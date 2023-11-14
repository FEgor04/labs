package com.efedorov.lab4.backend.adapter.out.persistence.user

import com.efedorov.lab4.backend.adapter.out.persistence.user.credentials.UserCredentialsPersistenceEntity
import com.efedorov.lab4.backend.application.port.`in`.exceptions.UserAlreadyExistsException
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.GetUserCredentialsPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.GetUserPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.PersistUserPort
import com.efedorov.lab4.backend.common.LoggerDelegate
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.ejb.EJB
import jakarta.ejb.Stateless
import jakarta.persistence.*
import org.hibernate.exception.ConstraintViolationException
import kotlin.jvm.Throws

@Stateless
class UserPersistenceAdapter : PersistUserPort, GetUserPort {
    private val logger by LoggerDelegate()

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @EJB
    private lateinit var getUserCredentialsPort: GetUserCredentialsPort


    @Throws(UserAlreadyExistsException::class)
    override fun signUpUser(email: String): User.withID {
        val newUser =
            UserPersistenceEntity(
                null,
                email,
                emptySet()
            )
        try {
            entityManager.persist(newUser)
            entityManager.flush()
        } catch (e: ConstraintViolationException) {
            logger.warn("Caught exception while creating new user: ${e.message}")
            throw UserAlreadyExistsException(email)
        }
        logger.info("User persisted succesfully with id = ${newUser.id}")
        return User.withID(User.UserID(newUser.id!!), newUser.email, emptySet())
    }

    override fun getUserByEmail(userName: String): User.withID? {
        val userPersistenceEntity = try {
            entityManager.createQuery("SELECT u FROM UserPersistenceEntity u WHERE u.email = :userName")
                .setParameter("userName", userName)
                .singleResult as UserPersistenceEntity
        } catch (e: NoResultException) {
            return null
        }
        return User.withID(
            User.UserID(userPersistenceEntity.id!!),
            userPersistenceEntity.email,
            userPersistenceEntity.credentials.map { UserCredentials.ID(it.id!!) }.toSet()
        )
    }

    override fun getUserById(id: User.UserID): User.withID? {
        val userPersistenceEntity = try {
            entityManager.createQuery("SELECT u FROM UserPersistenceEntity u WHERE u.id = :id")
                .setParameter("id", id.value)
                .singleResult as UserPersistenceEntity
        } catch (e: NoResultException) {
            return null
        }
        return User.withID(
            User.UserID(userPersistenceEntity.id!!),
            userPersistenceEntity.email,
            userPersistenceEntity.credentials.map { UserCredentials.ID(it.id!!) }.toSet()
        )
    }

    override fun getUserByCredentialsId(id: UserCredentials.ID): User.withID? {
        val userPersistenceEntity = try {
            entityManager.createQuery("SELECT u FROM UserPersistenceEntity u JOIN UserCredentialsPersistenceEntity c ON u = c.user WHERE c.id = :credentialsId ")
                .setParameter("credentialsId", id.value)
                .singleResult as UserPersistenceEntity
        } catch (e: NoResultException) {
            return null
        }
        return User.withID(
            User.UserID(userPersistenceEntity.id!!),
            userPersistenceEntity.email,
            userPersistenceEntity.credentials.map { UserCredentials.ID(it.id!!) }.toSet()
        )
    }
}