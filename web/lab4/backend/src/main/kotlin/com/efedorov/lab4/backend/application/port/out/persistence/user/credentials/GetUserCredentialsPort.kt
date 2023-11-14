package com.efedorov.lab4.backend.application.port.out.persistence.user.credentials

import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials

interface GetUserCredentialsPort {
    fun getCredentialsByCredentialsId(credentialsId: UserCredentials.ID): UserCredentials?
    fun getVkCredentialsByVkUserId(vkUserId: Long): UserCredentials.VkOauth?
    fun getGoogleCredentialsByEmail(email: String): UserCredentials.GoogleOauth?
    fun getPasswordCredentialsByEmail(email: String): UserCredentials.Password?
    fun getCredentialsByEmail(email: String): Set<UserCredentials>
}