package lab9.backend.adapter.`in`.web

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import lab9.backend.application.port.`in`.vehicles.GetVehiclesUseCase
import lab9.common.responses.ShowVehiclesResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@Validated
@RequestMapping("/api/vehicles")
class GetVehiclesController(
    private val getVehiclesUseCase: GetVehiclesUseCase,
    private val objectAdapter: WebObjectAdapter,
) {
    @GetMapping("")
    fun getVehicles(
        @RequestParam(defaultValue = "10") @Min(0) @Max(100) pageSize: Int,
        @RequestParam(defaultValue = "0") @Min(0) pageNumber: Int
    ): ResponseEntity<ShowVehiclesResponse> {
        val request = GetVehiclesRequest(pageSize = pageSize, pageNumber = pageNumber)
        val vehicles = getVehiclesUseCase.getVehicles(
            objectAdapter.showVehiclesRequestToQuery(request)
        )
        val response = ShowVehiclesResponse(
            vehicles.vehicles.map { objectAdapter.vehicleToResponse(it) }.toTypedArray(),
            totalPages = vehicles.totalPages,
            totalElements = vehicles.totalElements
        )
        return ResponseEntity.ok(response)
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): Map<String, String>? {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage: String = error.getDefaultMessage() ?: ""
            errors.put(fieldName, errorMessage)
        }
        return errors
    }
}