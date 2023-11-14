package com.efedorov.lab4.backend.application.port.`in`.signup

import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadAuthenticationException
import com.efedorov.lab4.backend.domain.User
import kotlin.jvm.Throws

interface SignInUseCase {
    @Throws(BadAuthenticationException::class)
    fun signIn(request: SignInRequest): User.withID

    data class SignInRequest(val email: String, val password: String)
}