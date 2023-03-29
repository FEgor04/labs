package lab7.entities.vehicle

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
        if (x <= xLowerBound) {
            throw ValidationException("x", "should be greater than -523")
        }
    }

    companion object {
        const val xLowerBound = -523
    }
}
