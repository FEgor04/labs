package com.efedorov.lab4.backend.adapter.out.persistence.user.credentials

import com.efedorov.lab4.backend.adapter.out.persistence.user.UserPersistenceEntity
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import java.time.Instant

@Entity
data class GoogleOAuthPersistenceEntity(
    @Id
    @GeneratedValue
    override var id: Long?,
    val accessToken: String,
    val expirationTime: Instant,
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    override var user: UserPersistenceEntity,
) : UserCredentialsPersistenceEntity(id, user) {
    fun toEntity(): UserCredentials.GoogleOauth {
        println("Converting google pers entity to domain entity. ${this}")
        return UserCredentials.GoogleOauth(UserCredentials.ID(id!!), accessToken, expirationTime, User.UserID(user.id!!))
    }
}
