package com.efedorov.lab4.backend.adapter.out.web.oauth

import com.efedorov.lab4.backend.application.port.out.web.oauth.BadOAuthCodeException
import com.efedorov.lab4.backend.application.port.out.web.oauth.GetVkAccessTokenPort
import com.efedorov.lab4.backend.common.DTO
import com.efedorov.lab4.backend.common.LoggerDelegate
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.ejb.Stateless
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.client.ClientBuilder
import jakarta.ws.rs.core.MediaType
import java.time.Instant
import java.time.Duration
import kotlin.jvm.Throws
import kotlin.math.exp

@Stateless
class VkOAuthAdapter : GetVkAccessTokenPort {
    private val logger by LoggerDelegate()
    private val vkClient = ClientBuilder.newClient();
    private val clientSecret = "VK_CLIENT_SECRET"
    private val clientId = "VK_CLIENT_ID"
    private val redirectUri = "https://localhost/oauth/vk"

    @Throws(BadOAuthCodeException::class)
    override fun get(code: String): GetVkAccessTokenPort.GetVkAccessTokenResponse {
        val webTarget = vkClient.target("https://oauth.vk.com")
        val authorizeWebTarget = webTarget.path("/access_token")
            .queryParam("client_id", clientId)
            .queryParam("client_secret", clientSecret)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("code", code)
        val response = try {
            authorizeWebTarget.request(MediaType.APPLICATION_JSON).get(VkResponse::class.java)
        } catch (e: WebApplicationException) {
            throw BadOAuthCodeException();
        }
        val expirationTime = Instant.now().plus(Duration.ofSeconds(response.expiresIn - 60))
        logger.info("Got response from VK oauth server. Email: ${response.email}, expires at $expirationTime")
        return GetVkAccessTokenPort.GetVkAccessTokenResponse(
            response.accessToken,
            response.userId,
            response.email,
            expirationTime
        )
    }


    @DTO
    data class VkResponse(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("expires_in")
        val expiresIn: Long,
        @JsonProperty("user_id")
        val userId: Long,
        @JsonProperty("email")
        val email: String,
    )
}
