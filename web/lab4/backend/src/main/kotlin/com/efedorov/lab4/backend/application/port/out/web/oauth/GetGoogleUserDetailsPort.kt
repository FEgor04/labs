package com.efedorov.lab4.backend.application.port.out.web.oauth

import kotlin.jvm.Throws

interface GetGoogleUserDetailsPort {
    @Throws(BadAccessTokenException::class)
    fun getUserDetails(accessToken: String): UserDetails

    data class UserDetails(
        val email: String,
        val googleId: Long,
    )
}