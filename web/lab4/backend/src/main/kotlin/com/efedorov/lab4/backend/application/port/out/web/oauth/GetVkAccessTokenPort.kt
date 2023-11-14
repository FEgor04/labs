package com.efedorov.lab4.backend.application.port.out.web.oauth

import java.time.Instant
import kotlin.jvm.Throws

interface GetVkAccessTokenPort {
    @Throws(BadOAuthCodeException::class)
    fun get(code: String): GetVkAccessTokenResponse

    data class GetVkAccessTokenResponse(val accessToken: String, val vkUserId: Long, val email: String, val expirationTime: Instant)


}