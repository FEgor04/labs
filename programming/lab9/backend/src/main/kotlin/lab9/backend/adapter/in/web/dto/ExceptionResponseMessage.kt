package lab9.backend.adapter.`in`.web.dto

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ExceptionResponseMessage(
    val time: String,
    val status: Int,
    val message: String?,
    val exception: String
) {
    companion object {
        fun fromException(e: Exception, status: Int): ExceptionResponseMessage {
            return ExceptionResponseMessage(
                time = Instant.now().toString(),
                status = status,
                exception = e::class.toString(),
                message = e.message,
            )
        }
    }
}