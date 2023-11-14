package com.efedorov.lab4.backend.adapter.`in`.web.filter

import com.efedorov.lab4.backend.application.port.`in`.auth.ValidateJwtTokenUseCase
import com.efedorov.lab4.backend.common.LoggerDelegate
import jakarta.ejb.EJB
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.container.PreMatching
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.SecurityContext
import jakarta.ws.rs.ext.Provider
import java.security.Principal

@PreMatching
@Provider
class JwtAuthencticationFilter : ContainerRequestFilter {
    private val jwtRegex = Regex("Bearer (.+)", RegexOption.IGNORE_CASE)
    private val allowedWithoutJwt = listOf("/refresh", "/signup", "/signin", "/vk", "/google", )

    @EJB
    private lateinit var validateTokenUseCase: ValidateJwtTokenUseCase


    private val logger by LoggerDelegate()

    override fun filter(requestContext: ContainerRequestContext) {
        if (
            allowedWithoutJwt.any { requestContext.uriInfo.absolutePath.toString().endsWith(it) }
        ) {
            logger.info("Request unsecured endpoint. not filtering it.")
            return
        }
        logger.info("Filtering request to ${requestContext.uriInfo.absolutePath}")
        val authorizationHeader: String? = requestContext.headers.getFirst("authorization")
        val token = jwtRegex.find(authorizationHeader ?: "")?.groups?.get(1)?.value
        if (token == null) {
            logger.info("JWT token is not set. Aborting request")
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                    .entity("JWT Token not set. Proceed to authorization page.")
                    .build()
            )
            return
        }
        logger.info("validating jwt token. token is $token")
        val user = validateTokenUseCase.validateToken(token)
        logger.info("token is valid")
        val securityContext = object : SecurityContext {
            override fun getUserPrincipal(): Principal {
                return Principal { user.email }
            }

            override fun isUserInRole(role: String?): Boolean {
                return false
            }

            override fun isSecure(): Boolean {
                return true
            }

            override fun getAuthenticationScheme(): String {
                return "jwt"
            }
        }
        requestContext.securityContext = securityContext
        logger.info("Set security context in filter")
    }
}