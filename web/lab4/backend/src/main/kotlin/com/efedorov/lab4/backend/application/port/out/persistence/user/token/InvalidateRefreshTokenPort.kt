package com.efedorov.lab4.backend.application.port.out.persistence.user.token

import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadRefreshTokenException
import kotlin.jvm.Throws

interface InvalidateRefreshTokenPort {
    /**
     * Помечает refresh-токен как невалидный
     */
    @Throws(BadRefreshTokenException::class)
    fun invalidateRefreshToken(tokenId: String)
}