package com.efedorov.lab4.backend.application.port.out.web.oauth

import java.time.Instant
import kotlin.jvm.Throws

interface GetGoogleAccessTokenPort {
    @Throws(BadOAuthCodeException::class)
    fun get(code: String): GetGoogleAccessTokenResponse

    data class GetGoogleAccessTokenResponse(
        val accessToken: String,
        val expirationTime: Instant
    )


}