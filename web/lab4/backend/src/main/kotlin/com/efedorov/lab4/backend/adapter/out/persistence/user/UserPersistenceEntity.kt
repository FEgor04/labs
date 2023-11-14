package com.efedorov.lab4.backend.adapter.out.persistence.user

import com.efedorov.lab4.backend.adapter.out.persistence.user.credentials.UserCredentialsPersistenceEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "users")
data class UserPersistenceEntity(
    @Id
    @GeneratedValue
    @Column(nullable = false)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    val email: String,
    @OneToMany
    val credentials: Set<UserCredentialsPersistenceEntity>
) : Serializable {
}