package com.efedorov.lab4.backend.application.port.out.persistence.user

import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials

interface GetUserPort {
    fun getUserByEmail(userName: String): User.withID?
    fun getUserById(id: User.UserID): User.withID?
    fun getUserByCredentialsId(id: UserCredentials.ID): User.withID?
}