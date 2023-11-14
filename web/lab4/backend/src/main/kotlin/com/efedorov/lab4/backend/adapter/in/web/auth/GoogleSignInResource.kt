package com.efedorov.lab4.backend.adapter.`in`.web.auth

import com.efedorov.lab4.backend.adapter.`in`.web.dto.UserDTO
import com.efedorov.lab4.backend.application.port.`in`.auth.GenerateSessionUseCase
import com.efedorov.lab4.backend.application.port.`in`.auth.oauth.SignInWithGoogleUseCase
import com.efedorov.lab4.backend.common.DTO
import jakarta.ejb.EJB
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response
import java.net.URI

@Path("/v1/signin/google")
class GoogleSignInResource {
    @EJB
    private lateinit var generateJwtTokenUseCase: GenerateSessionUseCase

    @EJB
    private lateinit var signInWithGoogleUseCase: SignInWithGoogleUseCase;

    @POST
    fun signIn(request: SignInWithGoogleRequest): Response {
        val user = signInWithGoogleUseCase.signInWithGoogle(request.code)
        val session = generateJwtTokenUseCase.generateSession(user)

        val response =
            Response
                .created(URI.create("/v1/users/${user.id.value}"))
                .entity(SignUpResource.SignUpResponse(session, UserDTO.fromEntity(user)))
                .build()
        return response
    }

    @DTO
    data class SignInWithGoogleRequest(val code: String)

    @DTO
    data class SignInResponse(val session: GenerateSessionUseCase.SessionDetails, val user: UserDTO)
}