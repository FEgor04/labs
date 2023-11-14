package com.efedorov.lab4.backend.application.port.`in`.auth

import com.efedorov.lab4.backend.domain.User
import kotlin.jvm.Throws

interface ValidateJwtTokenUseCase {
    @Throws(InvalidTokenException::class)
    fun validateToken(token: String): User.withID
}