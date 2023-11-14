package com.efedorov.lab4.backend.adapter.`in`.web.emapper

import com.efedorov.lab4.backend.application.port.`in`.exceptions.UserAlreadyExistsException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class UserAlreadyExistsExceptionMapper : ExceptionMapper<UserAlreadyExistsException> {
    override fun toResponse(exception: UserAlreadyExistsException?): Response = Response
        .status(Response.Status.CONFLICT)
        .entity("User with such username already exists")
        .build()
}