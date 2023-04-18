package services

import exceptions.BadUsernameOrPasswordException
import exceptions.ServiceUnavailable
import exceptions.UnauthorizedException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.resources.*
import lab9.common.dto.UserDTO
import kotlin.js.json

class UserService() {
    private val baseUrl = "/api"
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun tryToLogin(username: String, password: String) {
        val response: HttpResponse = client.submitForm(formParameters = Parameters.build {
            append("username", username)
            append("password", password)
        }) {
            url("$baseUrl/login")
        }
        if (response.status == HttpStatusCode.Unauthorized) {
            throw BadUsernameOrPasswordException()
        } else if (response.status.value >= 500) {
            throw ServiceUnavailable()
        }
    }

    suspend fun getMe(): UserDTO {
        val response: HttpResponse = client.get {
            url("$baseUrl/me")
        }
        if (response.status == HttpStatusCode.Unauthorized) {
            throw UnauthorizedException()
        } else if (response.status.value >= 500) {
            throw ServiceUnavailable()
        }
        return response.body()
    }
}