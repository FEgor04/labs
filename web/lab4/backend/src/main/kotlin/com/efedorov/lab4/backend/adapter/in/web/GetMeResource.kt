package com.efedorov.lab4.backend.adapter.`in`.web

import com.efedorov.lab4.backend.adapter.`in`.web.dto.UserDTO
import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadAuthenticationException
import com.efedorov.lab4.backend.application.port.`in`.user.GetUserUseCase
import jakarta.ejb.EJB
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.SecurityContext

@Path("/v1/me")
class GetMeResource {
    @EJB
    private lateinit var getUserUseCase: GetUserUseCase

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getMe(@Context securityContext: SecurityContext): UserDTO {
        val user = getUserUseCase.getUserByEmail(securityContext.userPrincipal.name)
            ?: throw BadAuthenticationException(securityContext.userPrincipal.name)
        return UserDTO.fromEntity(user)
    }

}