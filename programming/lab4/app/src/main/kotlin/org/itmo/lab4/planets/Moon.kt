package org.itmo.lab4.planets

import org.itmo.lab4.planets.surface.Surface

// луна это планета, да
class Moon(private var getSizeCallsCount: Int = 0) : Planet {
    /**
     * Возвращает видимый размер Луны на небе (какую часть неба она заполняет)
     */
    fun getSizeComparedToSky(): Double {
        return 0.5 * this.getSizeCallsCount++ + 0.5
    }

    class MoonSurface: Surface {
        enum class MoonSurfaceObjects {
            MOUNTAIN_RANGES,
            MOON_CIRCUSES,
            DEEP_CRACKS,
            DEEP_FAULTS,
        }
        override fun getObjects(): List<Any> {
            return MoonSurfaceObjects.values().map<MoonSurfaceObjects, Any> { it }
        }

        override fun toString(): String {
            return getObjects().map { it.toString() }.reduce {acc, next -> "$acc, $next"}
        }

        override fun hashCode(): Int {
            return toString().hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if(other is MoonSurface) {
                return toString() == other.toString()
            }
            return false
        }
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