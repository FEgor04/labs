package lab9.backend.adapter.`in`.web.dto

import kotlinx.serialization.Serializable
import org.springframework.web.bind.annotation.RequestParam

@Serializable
data class GetVehiclesSorting(
    @RequestParam(defaultValue = "ID", required = true)
    val sortingColumn: String,
    @RequestParam(defaultValue = "true", required = true)
    val ascending: Boolean,
)