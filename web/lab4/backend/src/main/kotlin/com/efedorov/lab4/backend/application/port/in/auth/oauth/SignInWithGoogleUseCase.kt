package com.efedorov.lab4.backend.application.port.`in`.auth.oauth

import com.efedorov.lab4.backend.application.port.out.web.oauth.BadOAuthCodeException
import com.efedorov.lab4.backend.domain.User
import kotlin.jvm.Throws

interface SignInWithGoogleUseCase {
    @Throws(BadOAuthCodeException::class)
    fun signInWithGoogle(code: String): User.withID
}