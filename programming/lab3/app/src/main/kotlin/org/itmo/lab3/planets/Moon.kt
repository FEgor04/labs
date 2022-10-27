package org.itmo.lab3.planets

import org.itmo.lab3.planets.surfaces.MoonSurface
import org.itmo.lab3.planets.surfaces.Surface

// луна это планета, да
class Moon : Planet {
    override fun getName(): String {
        return "Луна"
    }

    override fun getSurface(): Surface {
        return MoonSurface()
    }

    fun describe(): String {
        return "Луна"
    }

    override fun toString(): String {
        return "Луна"
    }
}