package com.efedorov.lab4.backend.application.service.auth.oauth

import com.efedorov.lab4.backend.application.port.`in`.auth.oauth.SignInWithGoogleUseCase
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.GetUserCredentialsPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.GetUserPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.PersistUserCredentialsPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.PersistUserPort
import com.efedorov.lab4.backend.application.port.out.web.oauth.BadOAuthCodeException
import com.efedorov.lab4.backend.application.port.out.web.oauth.GetGoogleAccessTokenPort
import com.efedorov.lab4.backend.application.port.out.web.oauth.GetGoogleUserDetailsPort
import com.efedorov.lab4.backend.common.LoggerDelegate
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.ejb.EJB
import jakarta.ejb.Stateless
import jakarta.transaction.Transactional
import kotlin.jvm.Throws

@Stateless
class SignInWithGoogleService : SignInWithGoogleUseCase {
    @EJB
    private lateinit var persistUserCredentialsPort: PersistUserCredentialsPort;

    @EJB
    private lateinit var persistUserPort: PersistUserPort;

    @EJB
    private lateinit var getUserPort: GetUserPort

    @EJB
    private lateinit var getUserCredentialsPort: GetUserCredentialsPort

    @EJB
    private lateinit var getGoogleAcessTokenPort: GetGoogleAccessTokenPort

    @EJB
    private lateinit var getGoogleUserDetailsPort: GetGoogleUserDetailsPort


    private val logger by LoggerDelegate()

    @Transactional
    @Throws(BadOAuthCodeException::class)
    override fun signInWithGoogle(code: String): User.withID {
        logger.info("Creating account for Google user.")
        val response = getGoogleAcessTokenPort.get(code)
        logger.info("Got access token from Google.")
        val userDetails = getGoogleUserDetailsPort.getUserDetails(response.accessToken)
        logger.info("Got user details from Google. Email is ${userDetails.email}")
        val credentials = getUserCredentialsPort.getGoogleCredentialsByEmail(userDetails.email)
        logger.info("Successfuly got credentials from database. Credentials ${credentials}")
        if (credentials != null) {
            logger.info("User with such Google credentials already exists. Returning it.")
            return getUserPort.getUserByEmail(userDetails.email)!!
        } else {
            logger.info("No user with such Google credentials.")
        }

        logger.info("Getting user by email.")
        val user = getUserPort.getUserByEmail(userDetails.email)
        if (user != null) {
            logger.info("User with such email already exists. Returning it.")
            logger.info("Persisting Google credentials")
            val credentialsId = persistUserCredentialsPort.persistUserCredentials(
                UserCredentials.GoogleOauth(
                    UserCredentials.ID(-1),
                    response.accessToken,
                    response.expirationTime,
                    user.id,
                )
            )
            logger.info("Successfully persisted credentials. ID: ${credentialsId.value}")
            return user;
        }
        val newUser = persistUserPort.signUpUser(userDetails.email);
        logger.info("Successfully persisted user. ID: ${newUser.id}")
        logger.info("Persisting Google credentials")
        val credentialsId = persistUserCredentialsPort.persistUserCredentials(
            UserCredentials.GoogleOauth(
                UserCredentials.ID(-1),
                response.accessToken,
                response.expirationTime,
                newUser.id,
            )
        )
        logger.info("Successfully persisted credentials. ID: ${credentialsId.value}")
        return newUser
    }

}