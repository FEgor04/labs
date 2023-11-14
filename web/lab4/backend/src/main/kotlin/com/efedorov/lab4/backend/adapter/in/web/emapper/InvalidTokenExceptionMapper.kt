package com.efedorov.lab4.backend.adapter.`in`.web.emapper

import com.efedorov.lab4.backend.application.port.`in`.auth.InvalidTokenException
import jakarta.ejb.Stateless
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class InvalidTokenExceptionMapper : ExceptionMapper<InvalidTokenException> {
    override fun toResponse(exception: InvalidTokenException?): Response {
        return Response
            .status(Response.Status.UNAUTHORIZED)
            .entity("Provided token is not valid.")
            .build()
    }
}