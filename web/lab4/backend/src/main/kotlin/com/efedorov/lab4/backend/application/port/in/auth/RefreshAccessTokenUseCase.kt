package com.efedorov.lab4.backend.application.port.`in`.auth

import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadRefreshTokenException
import kotlin.jvm.Throws

interface RefreshAccessTokenUseCase {
    @Throws(BadRefreshTokenException::class)
    fun updateRefreshToken(refreshToken: String): GenerateSessionUseCase.SessionDetails
}