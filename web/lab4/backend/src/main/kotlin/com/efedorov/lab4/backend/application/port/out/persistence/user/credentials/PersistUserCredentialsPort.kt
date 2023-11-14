package com.efedorov.lab4.backend.application.port.out.persistence.user.credentials

import com.efedorov.lab4.backend.domain.UserCredentials

interface PersistUserCredentialsPort {
    fun persistUserCredentials(credentials: UserCredentials): UserCredentials.ID
}