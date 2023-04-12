package lab8.entities.vehicle

import kotlinx.serialization.Serializable
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Дата-класс координат авто
 */
@Serializable
data class Coordinates(
    val x: Int,
    val y: Long?
): Comparable<Coordinates> {
    fun validate() {
        if (x <= xLowerBound) {
            throw ValidationException("x", "should be greater than -523")
        }
    }

    fun getLength(): Double {
        return sqrt( this.x.toDouble().pow(2) + (this.y?.toFloat()?.pow(2) ?: 0f) )
    }

    override fun compareTo(other: Coordinates): Int {
        return ( this.getLength() - other.getLength() ).toInt()
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    companion object {
        const val xLowerBound = -523
    }
}
