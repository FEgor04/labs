package lab9.backend.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason="User with such name already exists")
class UserAlreadyExistsException: Exception("User with such name already exists")