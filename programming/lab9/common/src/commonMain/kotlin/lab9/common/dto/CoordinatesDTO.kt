package lab9.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesDTO(
    val x: Int,
    val y: Long?,
) {
    init {
        require(x > -523) { "x should be greater than -523" }
    }
}
