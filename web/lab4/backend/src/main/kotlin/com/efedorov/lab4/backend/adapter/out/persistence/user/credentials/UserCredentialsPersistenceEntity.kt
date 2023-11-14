package com.efedorov.lab4.backend.adapter.out.persistence.user.credentials

import com.efedorov.lab4.backend.adapter.out.persistence.user.UserPersistenceEntity
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.persistence.*
import jakarta.validation.constraints.Email

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class UserCredentialsPersistenceEntity(
    @Id
    @GeneratedValue
    var id: Long?,
    @ManyToOne
    var user: UserPersistenceEntity
) {


    fun toDomainEntity() = when (this) {
        is GoogleOAuthPersistenceEntity -> UserCredentials.GoogleOauth(
            UserCredentials.ID(this.id!!),
            this.accessToken,
            this.expirationTime,
            User.UserID(this.user.id!!),
        )

        is PasswordCredentialsPersistenceEntity -> UserCredentials.Password(
            UserCredentials.ID(this.id!!),
            this.password,
            User.UserID(this.user.id!!),
        )

        is VKOAuthPersistenceEntity -> UserCredentials.VkOauth(
            UserCredentials.ID(this.id!!),
            this.accessToken,
            this.expirationTime,
            this.vkUserId,
            User.UserID(this.user.id!!),
        )

        else -> throw Exception("FUCK")
    }

    companion object {
        fun fromEntity(entity: UserCredentials): UserCredentialsPersistenceEntity = when (entity) {
            is UserCredentials.GoogleOauth -> GoogleOAuthPersistenceEntity(
                entity.id.value,
                entity.accessToken,
                entity.expirationTime,
                UserPersistenceEntity(id = entity.userId.value, "", emptySet()),
            )

            is UserCredentials.Password -> PasswordCredentialsPersistenceEntity(
                entity.id.value,
                UserPersistenceEntity(id = entity.userId.value, "", emptySet()),
                entity.password,
            )

            is UserCredentials.VkOauth -> VKOAuthPersistenceEntity(
                entity.id.value,
                UserPersistenceEntity(id = entity.userId.value, "", emptySet()),
                entity.accessToken,
                entity.expirationTime,
                entity.vkUserId,
            )
        }
    }
}