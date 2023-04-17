package lab9.backend.entities

import lab9.backend.exceptions.AppError
import lab9.backend.exceptions.ResourceNotFoundException
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun catchResourceNotFoundException(e: ResourceNotFoundException): ResponseEntity<AppError> {
        val error = AppError(HttpStatusCode.valueOf(404), e.message!!)
        return ResponseEntity.status(error.statusCode).body(error)
    }
}