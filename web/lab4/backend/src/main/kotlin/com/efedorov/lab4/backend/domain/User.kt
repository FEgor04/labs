package com.efedorov.lab4.backend.domain

sealed class User {
    abstract val email: String
    abstract val credentialsIDs: Set<UserCredentials.ID>

    data class withoutID(
        override val email: String, override val credentialsIDs: Set<UserCredentials.ID>
    ) : User()

    data class withID(
        val id: UserID,
        override val email: String, override val credentialsIDs: Set<UserCredentials.ID>
    ) : User()

    data class UserID(val value: Long)
}