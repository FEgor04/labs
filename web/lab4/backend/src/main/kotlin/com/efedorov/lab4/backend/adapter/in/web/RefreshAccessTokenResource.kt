package com.efedorov.lab4.backend.adapter.`in`.web

import com.efedorov.lab4.backend.application.port.`in`.auth.GenerateSessionUseCase
import com.efedorov.lab4.backend.application.port.`in`.auth.RefreshAccessTokenUseCase
import com.efedorov.lab4.backend.common.DTO
import jakarta.ejb.EJB
import jakarta.ejb.PostActivate
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/v1/refresh")
class RefreshAccessTokenResource {
    @EJB
    private lateinit var refreshAccessTokenUseCase: RefreshAccessTokenUseCase

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun refreshToken(request: RefreshTokenRequest): RefreshTokenResponse {
        val session = refreshAccessTokenUseCase.updateRefreshToken(request.refreshToken)
        return RefreshTokenResponse(session)
    }

    @DTO
    data class RefreshTokenRequest(val refreshToken: String)

    @DTO
    data class RefreshTokenResponse(val session: GenerateSessionUseCase.SessionDetails)
}