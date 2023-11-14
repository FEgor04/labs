package com.efedorov.lab4.backend.application.service.auth

import com.efedorov.lab4.backend.application.port.`in`.auth.PasswordEncoderUseCase
import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadAuthenticationException
import com.efedorov.lab4.backend.application.port.`in`.exceptions.UserHasNoPasswordAuthenticationException
import com.efedorov.lab4.backend.application.port.`in`.signup.SignInUseCase
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.GetUserCredentialsPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.GetUserPort
import com.efedorov.lab4.backend.common.LoggerDelegate
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.ejb.EJB
import jakarta.ejb.Stateless
import kotlin.jvm.Throws

@Stateless
class SignInServiceBean : SignInUseCase {
    @EJB
    private lateinit var passwordEncoder: PasswordEncoderUseCase

    @EJB
    private lateinit var getUserPort: GetUserPort

    @EJB
    private lateinit var getUserCredentialsPort: GetUserCredentialsPort

    private val logger by LoggerDelegate()

    @Throws(BadAuthenticationException::class)
    override fun signIn(request: SignInUseCase.SignInRequest): User.withID {
        val user = getUserPort.getUserByEmail(request.email) ?: throw BadAuthenticationException(request.email)
        logger.info("Got user from database. Id: ${user.id}")
        val userCredentials = getUserCredentialsPort.getPasswordCredentialsByEmail(request.email)
            ?: throw UserHasNoPasswordAuthenticationException()
        if (!passwordEncoder.checkPassword(request.password, userCredentials.password)) {
            throw BadAuthenticationException(request.email)
        }
        return user
    }
}