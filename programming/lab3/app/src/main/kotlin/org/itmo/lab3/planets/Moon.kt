package org.itmo.lab3.planets

import org.itmo.lab3.planets.surfaces.MoonSurface
import org.itmo.lab3.planets.surfaces.Surface

// луна это планета, да
class Moon(private var getSizeCallsCount: Int = 0) : Planet {
    /**
     * Возвращает видимый размер Луны на небе (какую часть неба она заполняет)
     */
    fun getSizeComparedToSky(): Double {
        return 0.5 * this.getSizeCallsCount++ + 0.5
    }

    override fun getName(): String {
        return "Луна"
    }

    override fun getSurface(): Surface {
        return MoonSurface()
    }

    override fun toString(): String {
        return "Луна"
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if(other is Moon) {
            return toString() == other.toString()
        }
        return false
    }
}