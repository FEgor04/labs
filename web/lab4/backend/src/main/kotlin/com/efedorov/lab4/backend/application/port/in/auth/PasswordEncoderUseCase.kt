package com.efedorov.lab4.backend.application.port.`in`.auth

import jakarta.ejb.Singleton

interface PasswordEncoderUseCase {
    fun encodePassword(rawPassword: String): String
    fun checkPassword(rawPassword: String, encodedPassword: String): Boolean
}