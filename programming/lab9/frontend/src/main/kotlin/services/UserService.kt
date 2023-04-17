package services

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
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
            url ("$baseUrl/login")
        }
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Something bad")
        }
    }
}