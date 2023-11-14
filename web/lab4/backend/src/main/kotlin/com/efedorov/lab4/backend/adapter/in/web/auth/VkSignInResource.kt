package com.efedorov.lab4.backend.adapter.`in`.web.auth

import com.efedorov.lab4.backend.adapter.`in`.web.dto.UserDTO
import com.efedorov.lab4.backend.application.port.`in`.auth.GenerateSessionUseCase
import com.efedorov.lab4.backend.application.port.`in`.auth.oauth.SignInWithVkUseCase
import com.efedorov.lab4.backend.common.DTO
import jakarta.ejb.EJB
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response
import java.net.URI

@Path("/v1/signin/vk")
class VkSignInResource {
    @EJB
    private lateinit var generateJwtTokenUseCase: GenerateSessionUseCase

    @EJB
    private lateinit var signInWithVkUseCase: SignInWithVkUseCase

    @POST
    fun signIn(request: SignInWithVkRequest): Response {
        val user = signInWithVkUseCase.signInWithVk(request.code)
        val session = generateJwtTokenUseCase.generateSession(user)

        val response =
            Response
                .created(URI.create("/v1/users/${user.id.value}"))
                .entity(SignUpResource.SignUpResponse(session, UserDTO.fromEntity(user)))
                .build()
        return response
    }

    @DTO
    data class SignInWithVkRequest(val code: String)

    @DTO
    data class SignUpResponse(val session: GenerateSessionUseCase.SessionDetails, val user: UserDTO)
}