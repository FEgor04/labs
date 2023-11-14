package com.efedorov.lab4.backend.adapter.`in`.web.auth

import com.efedorov.lab4.backend.adapter.`in`.web.dto.UserDTO
import com.efedorov.lab4.backend.application.port.`in`.auth.GenerateSessionUseCase
import com.efedorov.lab4.backend.application.port.`in`.signup.SignInUseCase
import com.efedorov.lab4.backend.common.DTO
import com.efedorov.lab4.backend.common.LoggerDelegate
import jakarta.ejb.EJB
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.net.URI

@Path("/v1/signin")
class SignInResource {
    private val logger by LoggerDelegate()

    @EJB
    private lateinit var signInUse: SignInUseCase

    @EJB
    private lateinit var generateJwtTokenUseCase: GenerateSessionUseCase

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun signUp(request: SignInRequest): Response {
        logger.info("Signing in user ${request.email}")
        val user = signInUse.signIn(request = SignInUseCase.SignInRequest(request.email, request.password))
        logger.info("User created with ID = ${user.id}")
        val session = generateJwtTokenUseCase.generateSession(user)
        return Response
            .created(URI.create("/v1/users/${user.id.value}"))
            .entity(SignInResponse(session, UserDTO.fromEntity(user)))
            .build()
    }

    @DTO
    class SignInRequest {
        lateinit var email: String
        lateinit var password: String
    }

    @DTO
    data class SignInResponse(val session: GenerateSessionUseCase.SessionDetails, val user: UserDTO)
}