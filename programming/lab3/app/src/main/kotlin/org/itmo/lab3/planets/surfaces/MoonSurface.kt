package org.itmo.lab3.planets.surfaces

import org.itmo.lab3.planets.surfaces.objects.MoonSurfaceObjects


class MoonSurface: Surface {
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