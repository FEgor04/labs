package com.efedorov.lab4.backend.adapter.`in`.web.emapper

import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadAuthenticationException
import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadRefreshTokenException
import com.efedorov.lab4.backend.application.port.`in`.exceptions.UserAlreadyExistsException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class BadRefreshTokenExceptionMapper : ExceptionMapper<BadRefreshTokenException> {
    override fun toResponse(exception: BadRefreshTokenException): Response = Response
        .status(Response.Status.UNAUTHORIZED)
        .entity("Bad refresh token")
        .build()
}