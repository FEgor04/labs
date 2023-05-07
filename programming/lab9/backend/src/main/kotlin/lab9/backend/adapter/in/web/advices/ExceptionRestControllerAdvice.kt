package lab9.backend.adapter.`in`.web.advices

import jakarta.validation.ConstraintViolationException
import lab9.backend.adapter.`in`.web.dto.ExceptionResponseMessage
import lab9.backend.application.port.`in`.signup.UserAlreadyExistsException
import lab9.backend.application.port.`in`.user.PermissionDeniedException
import lab9.backend.application.port.`in`.vehicles.VehicleAlreadyExistsException
import lab9.backend.application.port.`in`.vehicles.VehicleNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionRestControllerAdvice {
    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleInvalidRequestException(ex: Exception): ExceptionResponseMessage {
        return ExceptionResponseMessage.fromException(ex, HttpStatus.BAD_REQUEST.value())
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleUserAlreadyExistsException(ex: Exception): ExceptionResponseMessage {
        return ExceptionResponseMessage.fromException(ex, HttpStatus.CONFLICT.value())
    }

    @ExceptionHandler(VehicleAlreadyExistsException::class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    fun handleVehicleAlreadyExistsException(ex: Exception): ExceptionResponseMessage {
        return ExceptionResponseMessage.fromException(ex, HttpStatus.CONFLICT.value())
    }

    @ExceptionHandler(VehicleNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleVehicleNotFoundException(ex: Exception): ExceptionResponseMessage {
        return ExceptionResponseMessage.fromException(ex, HttpStatus.CONFLICT.value())
    }

     @ExceptionHandler(PermissionDeniedException::class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    fun handlePermissionDeniedException(ex: Exception): ExceptionResponseMessage {
        return ExceptionResponseMessage.fromException(ex, HttpStatus.CONFLICT.value())
    }

}