package com.efedorov.lab4.backend.application.service.auth

import com.efedorov.lab4.backend.application.port.`in`.auth.PasswordEncoderUseCase
import com.efedorov.lab4.backend.application.port.`in`.signup.SignUpByPasswordUseCase
import com.efedorov.lab4.backend.application.port.`in`.exceptions.UserAlreadyExistsException
import com.efedorov.lab4.backend.application.port.out.persistence.user.GetUserPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.PersistUserCredentialsPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.PersistUserPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.credentials.GetUserCredentialsPort
import com.efedorov.lab4.backend.common.LoggerDelegate
import com.efedorov.lab4.backend.domain.User
import com.efedorov.lab4.backend.domain.UserCredentials
import jakarta.ejb.EJB
import jakarta.ejb.Stateless
import jakarta.transaction.Transactional
import java.io.Serializable
import kotlin.jvm.Throws


@Stateless
class SignUpByPasswordServiceBean : SignUpByPasswordUseCase, Serializable {

    @EJB
    private lateinit var passwordEncoder: PasswordEncoderUseCase

    @EJB
    private lateinit var persistUserPort: PersistUserPort

    @EJB
    private lateinit var persistUserCredentialsPort: PersistUserCredentialsPort

    @EJB
    private lateinit var getUserPort: GetUserPort
    @EJB
    private lateinit var getUserCredentialsPort: GetUserCredentialsPort

    @Throws(UserAlreadyExistsException::class)
    @Transactional
    override fun signUpUser(request: SignUpByPasswordUseCase.SignUpRequest): User.withID {
        logger.info("Signing up new user with email = ${request.email}")
        val user = getUserPort.getUserByEmail(request.email)
        if(user != null) {
            logger.warn("User with such email already exists.")
            throw UserAlreadyExistsException(request.email);
        }

        val encodedPassword = passwordEncoder.encodePassword(request.password)
        val newUser = persistUserPort.signUpUser(request.email)
        val creds = persistUserCredentialsPort.persistUserCredentials(
            UserCredentials.Password(
                UserCredentials.ID(-1),
                encodedPassword,
                userId = newUser.id
            )
        )
        return newUser;
    }

    companion object {
        private val logger by LoggerDelegate()
    }
}