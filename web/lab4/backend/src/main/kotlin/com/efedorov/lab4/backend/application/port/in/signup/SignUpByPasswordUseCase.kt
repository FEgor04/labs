package com.efedorov.lab4.backend.application.port.`in`.signup

import com.efedorov.lab4.backend.application.port.`in`.exceptions.UserAlreadyExistsException
import com.efedorov.lab4.backend.domain.User
import kotlin.jvm.Throws

interface SignUpByPasswordUseCase {
    @Throws(UserAlreadyExistsException::class)
    fun signUpUser(request: SignUpRequest): User.withID

    data class SignUpRequest(val email: String, val password: String)
}