package lab9.backend.exceptions

import org.springframework.http.HttpStatusCode

data class AppError(
    val statusCode: HttpStatusCode,
    val message: String,
) {
}