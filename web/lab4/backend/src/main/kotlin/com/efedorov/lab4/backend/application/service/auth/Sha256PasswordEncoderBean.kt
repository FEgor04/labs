package com.efedorov.lab4.backend.application.service.auth

import com.efedorov.lab4.backend.application.port.`in`.auth.PasswordEncoderUseCase
import jakarta.ejb.Stateless
import java.io.Serializable
import java.security.MessageDigest

@Stateless
class Sha256PasswordEncoderBean : PasswordEncoderUseCase, Serializable {
    private val messageDigest = MessageDigest.getInstance("SHA-256")
    private val inputCharset = Charsets.UTF_8

    @OptIn(ExperimentalStdlibApi::class)
    override fun encodePassword(rawPassword: String): String {
        return messageDigest.digest(rawPassword.toByteArray(inputCharset)).toHexString(HexFormat.Default)
    }

    override fun checkPassword(rawPassword: String, encodedPassword: String): Boolean {
        return this.encodePassword(rawPassword) == encodedPassword
    }
}