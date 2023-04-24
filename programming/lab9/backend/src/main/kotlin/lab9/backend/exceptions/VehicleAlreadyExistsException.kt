package lab9.backend.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(reason = "Vehicle with such name already exists", code = HttpStatus.BAD_REQUEST)
class VehicleAlreadyExistsException: Exception("Vehicle with such name already exists")