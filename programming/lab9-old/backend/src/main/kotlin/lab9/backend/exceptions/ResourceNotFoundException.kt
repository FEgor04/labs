package lab9.backend.exceptions

class ResourceNotFoundException(override val message: String?): RuntimeException(message)