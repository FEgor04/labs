package com.efedorov.lab4.backend.adapter.out.persistence.token

import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadRefreshTokenException
import com.efedorov.lab4.backend.application.port.out.persistence.user.token.InvalidateRefreshTokenPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.token.IssueRefreshTokenIdPort
import jakarta.ejb.Stateless
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.util.UUID
import kotlin.jvm.Throws

@Stateless
class RefreshTokenPersistenceAdapter : InvalidateRefreshTokenPort, IssueRefreshTokenIdPort {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Throws(BadRefreshTokenException::class)
    override fun invalidateRefreshToken(tokenId: String) {
        val updatedCnt =
            entityManager.createQuery("UPDATE RefreshTokenPersistenceEntity t SET isValid = false WHERE t.id = :id AND isValid = true")
                .setParameter("id", tokenId)
                .executeUpdate()
        if (updatedCnt == 0) {
            throw BadRefreshTokenException()
        }
    }

    override fun issueRefreshTokenId(): String {
        val tokenId = UUID.randomUUID().toString()
        val token = RefreshTokenPersistenceEntity(tokenId, true)
        entityManager.persist(token)
        entityManager.flush()
        return token.id
    }
}