package lab9.backend.adapter.`in`.web.dto
import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesDTO(
        val x: Int,
        val y: Long?
)