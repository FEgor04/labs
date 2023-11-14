package com.efedorov.lab4.backend.application.service.auth.oauth

import com.efedorov.lab4.backend.adapter.out.web.oauth.VkOAuthAdapter
import com.efedorov.lab4.backend.application.port.`in`.auth.oauth.SignInWithVkUseCase
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.GetUserCredentialsPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.GetUserPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.PersistUserCredentialsPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.PersistUserPort
import com.efedorov.lab4.backend.application.port.out.web.oauth.BadOAuthCodeException
import com.efedorov.lab4.backend.application.port.out.web.oauth.GetVkAccessTokenPort
import com.efedorov.lab4.backend.common.LoggerDelegate
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.ejb.EJB
import jakarta.ejb.Stateless
import jakarta.transaction.Transactional
import kotlin.jvm.Throws

@Stateless
class SignInWithVkService : SignInWithVkUseCase {
    @EJB
    private lateinit var persistUserCredentialsPort: PersistUserCredentialsPort;

    @EJB
    private lateinit var persistUserPort: PersistUserPort;

    @EJB
    private lateinit var getUserPort: GetUserPort

    @EJB
    private lateinit var getUserCredentialsPort: GetUserCredentialsPort

    @EJB
    private lateinit var getVkAccessTokenPort: GetVkAccessTokenPort


    private val logger by LoggerDelegate()

    @Transactional
    @Throws(BadOAuthCodeException::class)
    override fun signInWithVk(code: String): User.withID {
        val response = getVkAccessTokenPort.get(code)
        val credentials = getUserCredentialsPort.getVkCredentialsByVkUserId(response.vkUserId)
        if (credentials != null) {
            logger.info("User with such VK credentials already exists. Returning it.")
            return getUserPort.getUserByCredentialsId(credentials.id)!!
        }


        val user = getUserPort.getUserByEmail(response.email)
        if (user != null) {
            logger.info("User with such email already exists. Returning it.")
            logger.info("Persisting VK credentials")
            val credentialsId = persistUserCredentialsPort.persistUserCredentials(
                UserCredentials.VkOauth(
                    UserCredentials.ID(-1),
                    response.accessToken,
                    response.expirationTime,
                    response.vkUserId,
                    user.id
                )
            )
            logger.info("Successfully persisted credentials. ID: ${credentialsId.value}")
            return user;
        }
        val newUser = persistUserPort.signUpUser(response.email)
        logger.info("Successfully persisted user. ID: ${newUser.id}")
        logger.info("Persisting VK credentials")
        val credentialsId = persistUserCredentialsPort.persistUserCredentials(
            UserCredentials.VkOauth(
                UserCredentials.ID(-1),
                response.accessToken,
                response.expirationTime,
                response.vkUserId,
                newUser.id
            )
        )
        logger.info("Successfully persisted credentials. ID: ${credentialsId.value}")
        return newUser
    }

}