package com.efedorov.lab4.backend.application.service.auth

import com.efedorov.lab4.backend.application.port.`in`.auth.GenerateSessionUseCase
import com.efedorov.lab4.backend.application.port.`in`.auth.InvalidTokenException
import com.efedorov.lab4.backend.application.port.`in`.auth.RefreshAccessTokenUseCase
import com.efedorov.lab4.backend.application.port.`in`.auth.ValidateJwtTokenUseCase
import com.efedorov.lab4.backend.application.port.`in`.exceptions.BadRefreshTokenException
import com.efedorov.lab4.backend.application.port.`in`.user.GetUserUseCase
import com.efedorov.lab4.backend.application.port.out.persistence.user.token.InvalidateRefreshTokenPort
import com.efedorov.lab4.backend.application.port.out.persistence.user.token.IssueRefreshTokenIdPort
import com.efedorov.lab4.backend.common.LoggerDelegate
import com.efedorov.lab4.backend.domain.User
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.SIG
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.ejb.EJB
import jakarta.ejb.Stateless
import java.time.Duration
import java.time.Instant
import java.util.*
import kotlin.jvm.Throws

@Stateless
class JwtTokenServiceBean : GenerateSessionUseCase, ValidateJwtTokenUseCase, RefreshAccessTokenUseCase {
    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("2a448c99dfb462d6618cc8856e9507097a772dee445a4e68983b8383246c3046")) // TODO: load key from environment variables
    private val accessTokenTimeToLive = Duration.ofMinutes(20)
    private val refreshTokenTimeToLive = Duration.ofDays(7)
    private val logger by LoggerDelegate()

    @EJB
    private lateinit var issueRefreshTokenPort: IssueRefreshTokenIdPort
    @EJB
    private lateinit var invalidateRefreshTokenPort: InvalidateRefreshTokenPort
    @EJB
    private lateinit var getUserUseCase: GetUserUseCase

    override fun generateSession(user: User.withID): GenerateSessionUseCase.SessionDetails {
        val issuedTime = Instant.now()
        val refreshToken = generateRefreshToken(user, issuedTime)
        val accessToken = generateAccessToken(user, issuedTime)
        return GenerateSessionUseCase.SessionDetails(accessToken, refreshToken)
    }

    private fun generateRefreshToken(user: User.withID, issuedTime: Instant = Instant.now()): String {
        val refreshTokenId = issueRefreshTokenPort.issueRefreshTokenId()
        val expirationTime = issuedTime.plus(refreshTokenTimeToLive)

        return Jwts.builder()
            .issuer("ejb-monolith")
            .id(refreshTokenId)
            .claim("email", user.email)
            .issuedAt(Date.from(issuedTime))
            .expiration(Date.from(expirationTime))
            .signWith(key, SIG.HS256)
            .compact()
    }

    private fun generateAccessToken(user: User.withID, issuedTime: Instant = Instant.now()): String {
        val expirationTime = issuedTime.plus(accessTokenTimeToLive)

        return Jwts.builder()
            .issuer("ejb-monolith")
            .subject(user.id.value.toString())
            .claim("email", user.email)
            .issuedAt(Date.from(issuedTime))
            .expiration(Date.from(expirationTime))
            .signWith(key, SIG.HS256)
            .compact()
    }

    @Throws(InvalidTokenException::class)
    override fun validateToken(token: String): User.withID {
        try {
            val jwt = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)

            val id = jwt.payload.subject.toLong()
            val userName: String = jwt.payload["email"] as String
            return User.withID(User.UserID(id), userName, emptySet()) // ну пустой и пустой... че такого то
        } catch (e: JwtException) {
            logger.error("Token is not valid! $e")
            throw InvalidTokenException()
        }
    }

    @Throws(BadRefreshTokenException::class)
    override fun updateRefreshToken(refreshToken: String): GenerateSessionUseCase.SessionDetails {
        try {
            val jwt = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(refreshToken)

            val tokenId = jwt.payload.id
            logger.info("Refreshing token with id = $tokenId")
            invalidateRefreshTokenPort.invalidateRefreshToken(tokenId)
            val email: String = jwt.payload["email"] as String
            val user = getUserUseCase.getUserByEmail(email)!!
            return generateSession(user)
        } catch (e: JwtException) {
            logger.error("Refresh token is not valid! $e")
            throw BadRefreshTokenException()
        }
    }

}