package com.efedorov.lab4.backend.adapter.out.web.oauth

import com.efedorov.lab4.backend.application.port.out.web.oauth.BadOAuthCodeException
import com.efedorov.lab4.backend.application.port.out.web.oauth.GetGoogleAccessTokenPort
import com.efedorov.lab4.backend.application.port.out.web.oauth.GetGoogleUserDetailsPort
import com.efedorov.lab4.backend.common.DTO
import com.efedorov.lab4.backend.common.LoggerDelegate
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.ejb.Stateless
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.client.ClientBuilder
import jakarta.ws.rs.client.Entity
import jakarta.ws.rs.core.MediaType
import java.time.Instant
import java.time.Duration
import kotlin.jvm.Throws

@Stateless
class GoogleOAuthAdapter : GetGoogleAccessTokenPort, GetGoogleUserDetailsPort {
    private val logger by LoggerDelegate()
    private val googleClient = ClientBuilder.newClient();
    private val clientSecret = "GOOGLE_CLIENT_SECRET" // DELETE BEFORE PUSH!!!
    private val clientId = "GOOGLE_CLIENT_ID"
    private val redirectUri = "https://localhost/oauth/google"

    @Throws(BadOAuthCodeException::class)
    override fun get(code: String): GetGoogleAccessTokenPort.GetGoogleAccessTokenResponse {
        logger.info("Getting access token for user.")
        val webTarget = googleClient.target("https://oauth2.googleapis.com")
        val authorizeWebTarget = webTarget.path("/token")
            .queryParam("client_id", clientId)
            .queryParam("client_secret", clientSecret)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("code", code)
            .queryParam("grant_type", "authorization_code")
        val response = try {
            authorizeWebTarget.request(MediaType.APPLICATION_JSON).post(Entity.text(""))
                .readEntity(GoogleResponse::class.java)
        } catch (e: WebApplicationException) {
            logger.warn("Bad access token! Error: $e")
            throw BadOAuthCodeException();
        }
        val expirationTime = Instant.now().plus(Duration.ofSeconds(response.expiresIn - 60))
        logger.info("Got response from Google OAuth server. Token expires at ${expirationTime}")
        return GetGoogleAccessTokenPort.GetGoogleAccessTokenResponse(
            accessToken = response.accessToken,
            expirationTime = expirationTime,
        )
    }


    @DTO
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class GoogleResponse(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("expires_in")
        val expiresIn: Long,
    )

    override fun getUserDetails(accessToken: String): GetGoogleUserDetailsPort.UserDetails {
        val webTarget = googleClient.target("https://www.googleapis.com")
        val response = webTarget.path("/userinfo/v2/me")
            .request(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${accessToken}")
            .get(GetMeResponse::class.java)
        return GetGoogleUserDetailsPort.UserDetails(response.email, response.googleId)
    }

    @DTO
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class GetMeResponse(
        val googleId: Long,
        val email: String,
    )
}
