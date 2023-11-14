package com.efedorov.lab4.backend.adapter.out.persistence.user.credentials

import com.efedorov.lab4.backend.adapter.out.persistence.user.UserPersistenceEntity
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.persistence.*
import jakarta.validation.constraints.Email

@Entity
data class PasswordCredentialsPersistenceEntity(
    @Id
    @GeneratedValue
    override var id: Long?,
    @OneToOne(fetch = FetchType.EAGER)
    override var user: UserPersistenceEntity,
    val password: String,
) :
    UserCredentialsPersistenceEntity(id, user) {
    fun toEntity() = UserCredentials.Password(UserCredentials.ID(this.id!!), password, User.UserID(user.id!!))
}
