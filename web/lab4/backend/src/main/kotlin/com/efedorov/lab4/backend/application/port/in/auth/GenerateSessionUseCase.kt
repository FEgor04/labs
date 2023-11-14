package com.efedorov.lab4.backend.application.port.`in`.auth

import com.efedorov.lab4.backend.domain.User

interface GenerateSessionUseCase {
    fun generateSession(user: User.withID): SessionDetails
    
    data class SessionDetails(val accessToken: String, val refreshToken: String)
}