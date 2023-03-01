package lab6.shared.entities.vehicle

import kotlinx.serialization.Serializable

/**
 * Дата-класс координат авто
 */
@Serializable
data class Coordinates(
    val x: Int,
    val y: Long?
) {
    fun validate() {
        if (x <= -523) {
            throw ValidationException("x", "should be greater than -523")
        }
    }
}