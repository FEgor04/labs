package com.efedorov.lab4.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Table
import java.time.Instant

sealed class UserCredentials {
    abstract val id: ID;
    abstract val userId: User.UserID

    data class Password(
        override val id: ID,
        val password: String, override val userId: User.UserID,
    ) : UserCredentials()

    data class VkOauth(
        override val id: ID,
        val accessToken: String,
        val expirationTime: Instant,
        val vkUserId: Long,
        override val userId: User.UserID,
    ) : UserCredentials()


    data class GoogleOauth(
        override val id: ID,
        val accessToken: String,
        val expirationTime: Instant, override val userId: User.UserID,
    ) : UserCredentials()


    data class ID(val value: Long)
}