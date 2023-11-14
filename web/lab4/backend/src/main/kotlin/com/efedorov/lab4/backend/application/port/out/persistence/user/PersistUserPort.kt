package com.efedorov.lab4.backend.application.port.out.persistence.user

import com.efedorov.lab4.backend.application.port.`in`.exceptions.UserAlreadyExistsException
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import kotlin.jvm.Throws

interface PersistUserPort {
    @Throws(UserAlreadyExistsException::class)
    fun signUpUser(presentationName: String): User.withID
}