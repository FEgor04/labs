package lab5.entities.vehicle

import lab5.entities.ValidationException

/**
 * Дата-класс координат авто
 */
data class Coordinates(val x: Int, val y: Long?) {
    fun validate() {
        if(x <= -523) {
            throw ValidationException("x", "should be greater than -523")
        }
    }
}