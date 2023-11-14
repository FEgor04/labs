package com.efedorov.lab4.backend.adapter.`in`.web.auth

import com.efedorov.lab4.backend.adapter.`in`.web.dto.UserDTO
import com.efedorov.lab4.backend.application.port.`in`.auth.GenerateSessionUseCase
import com.efedorov.lab4.backend.application.port.`in`.signup.SignUpByPasswordUseCase
import com.efedorov.lab4.backend.common.DTO
import com.efedorov.lab4.backend.common.LoggerDelegate
import jakarta.ejb.EJB
import jakarta.validation.constraints.Email
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.net.URI

@Path("/v1/signup")
class SignUpResource {
    private val logger by LoggerDelegate()

    @EJB
    private lateinit var signUpByPasswordUseCase: SignUpByPasswordUseCase

    @EJB
    private lateinit var generateJwtTokenUseCase: GenerateSessionUseCase

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun signUp(request: SignUpRequest): Response {
        logger.info("Signing up new user ${request.email}")
        val user = signUpByPasswordUseCase.signUpUser(request = SignUpByPasswordUseCase.SignUpRequest(request.email, request.password))
        logger.info("User created with ID = ${user.id}")
        val session = generateJwtTokenUseCase.generateSession(user)
        val response =
            Response
                .created(URI.create("/v1/users/${user.id.value}"))
                .entity(SignUpResponse(session, UserDTO.fromEntity(user)))
                .build()
        return response
    }

    @DTO
    class SignUpRequest {
        @Email
        lateinit var email: String
        lateinit var password: String
    }

    @DTO
    data class SignUpResponse(val session: GenerateSessionUseCase.SessionDetails, val user: UserDTO)
}