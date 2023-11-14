package com.efedorov.lab4.backend.application.port.`in`.user

import com.efedorov.lab4.backend.domain.User

interface GetUserUseCase {
    fun getUserByEmail(email: String): User.withID?
}