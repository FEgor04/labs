package com.efedorov.lab4.backend.adapter.out.persistence.token

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "refresh_token")
data class RefreshTokenPersistenceEntity(
    @Id
    val id: String,
    val isValid: Boolean
)