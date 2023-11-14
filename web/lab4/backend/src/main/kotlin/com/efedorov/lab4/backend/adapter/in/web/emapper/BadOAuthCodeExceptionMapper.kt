package com.efedorov.lab4.backend.adapter.`in`.web.emapper

import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadAuthenticationException
import com.efedorov.lab4.backend.application.port.`in`.exceptions.UserAlreadyExistsException
import com.efedorov.lab4.backend.application.port.out.web.oauth.BadOAuthCodeException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class BadOAuthCodeExceptionMapper : ExceptionMapper<BadOAuthCodeException> {
    override fun toResponse(exception: BadOAuthCodeException): Response = Response
        .status(Response.Status.UNAUTHORIZED)
        .entity(exception.message)
        .build()
}