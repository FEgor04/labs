package com.efedorov.lab4.backend.adapter.out.persistence.user.credentials

import com.efedorov.lab4.backend.adapter.out.persistence.user.UserPersistenceEntity
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.GetUserCredentialsPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.PersistUserCredentialsPort
import com.efedorov.lab4.backend.common.LoggerDelegate
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.ejb.Stateless
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext

@Stateless
class UserCredentialsAdapter : GetUserCredentialsPort, PersistUserCredentialsPort {
    private val logger by LoggerDelegate()

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    private fun fetchUserByEmail(email: String): UserPersistenceEntity {
        return entityManager.createQuery(
            "SELECT u from UserPersistenceEntity u where u.email = :email",
            UserPersistenceEntity::class.java
        )
            .setParameter("email", email)
            .singleResult
    }

    private fun fetchUserByVkId(id: Long): UserPersistenceEntity {
        return entityManager.createQuery(
            "SELECT u FROM UserPersistenceEntity u JOIN VKOAuthPersistenceEntity v ON v.user = u WHERE v.vkUserId = :vkUserId",
            UserPersistenceEntity::class.java
        )
            .setParameter("vkUserId", id)
            .singleResult
    }

    override fun getCredentialsByCredentialsId(credentialsId: UserCredentials.ID): UserCredentials? {
        val result =
            (entityManager.createQuery("SELECT c from UserCredentialsPersistenceEntity c WHERE c.id = :credentialsId")
                .setParameter("credentialsId", credentialsId.value)
                .singleResult) as UserCredentialsPersistenceEntity
        return result.toDomainEntity();
    }

    override fun getVkCredentialsByVkUserId(vkUserId: Long): UserCredentials.VkOauth? {
        return try {
            val res =
                (entityManager.createQuery("SELECT c from VKOAuthPersistenceEntity c inner join fetch c.user where c.vkUserId = :vkUserId", VKOAuthPersistenceEntity::class.java)
                    .setParameter("vkUserId", vkUserId)
                    .singleResult)
            res.user = fetchUserByVkId(vkUserId);
            res.toEntity()
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun getGoogleCredentialsByEmail(email: String): UserCredentials.GoogleOauth? {
        logger.info("Getting google credentials for email = ${email}")
        return try {
            val res = (entityManager.createQuery(
                "select c FROM GoogleOAuthPersistenceEntity c INNER JOIN FETCH c.user WHERE c.user.email = :email",
                GoogleOAuthPersistenceEntity::class.java
            )
                .setParameter("email", email)
                .singleResult)
            res.user = fetchUserByEmail(email);
            res.toEntity()
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun getPasswordCredentialsByEmail(email: String): UserCredentials.Password? {
        return try {
            val res =
                (entityManager.createQuery("SELECT c from PasswordCredentialsPersistenceEntity c inner join fetch c.user where c.user.email = :email")
                    .setParameter("email", email)
                    .singleResult as PasswordCredentialsPersistenceEntity)
            res.user = fetchUserByEmail(email);
            res.toEntity()
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun getCredentialsByEmail(email: String): Set<UserCredentials> {
        return try {
            entityManager.createQuery("SELECT c from UserCredentialsPersistenceEntity c where c.user.email = :email")
                .setParameter("email", email)
                .resultList
                .map { (it as UserCredentialsPersistenceEntity).toDomainEntity() }
                .toSet()
        } catch (e: NoResultException) {
            return emptySet()
        }

    }

    override fun persistUserCredentials(credentials: UserCredentials): UserCredentials.ID {
        val persistenceEntity = UserCredentialsPersistenceEntity.fromEntity(credentials)
        persistenceEntity.id = null
        logger.info("Going to persist $persistenceEntity")
        try {
            entityManager.persist(persistenceEntity)
            entityManager.flush()
        } catch (e: Exception) {
            throw e
        }
        return UserCredentials.ID(persistenceEntity.id!!)
    }

}