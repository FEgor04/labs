package com.efedorov.lab4.backend.adapter.out.persistence.user.credentials

import com.efedorov.lab4.backend.adapter.out.persistence.user.UserPersistenceEntity
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import java.time.Instant

@Entity
data class VKOAuthPersistenceEntity(
    @Id
    @GeneratedValue
    override var id: Long?,
    @OneToOne(fetch = FetchType.EAGER)
    override var user: UserPersistenceEntity,
    val accessToken: String,
    val expirationTime: Instant,
    @Column(unique = true, nullable = false)
    val vkUserId: Long,
) : UserCredentialsPersistenceEntity(id, user) {
    fun toEntity() = UserCredentials.VkOauth(
        UserCredentials.ID(id!!),
        accessToken,
        expirationTime,
        vkUserId,
        userId = User.UserID(user.id!!)
    )
}
