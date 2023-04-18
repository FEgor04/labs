package exceptions

sealed class UserServiceException(private val msg: String): Exception(msg)
class ServiceUnavailable(): UserServiceException("Service is currently unavailable")
class UnauthorizedException(): UserServiceException("You are not authorized. Proceed to login page")
