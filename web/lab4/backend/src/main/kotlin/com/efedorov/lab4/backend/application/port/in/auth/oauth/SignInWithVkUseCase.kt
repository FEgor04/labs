package com.efedorov.lab4.backend.application.port.`in`.auth.oauth

import com.efedorov.lab4.backend.application.port.out.web.oauth.BadOAuthCodeException
import com.efedorov.lab4.backend.domain.User
import kotlin.jvm.Throws

interface SignInWithVkUseCase {
    @Throws(BadOAuthCodeException::class)
    fun signInWithVk(code: String): User.withID
}