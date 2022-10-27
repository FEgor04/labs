package org.itmo.lab3.look.looker

import org.itmo.lab3.planets.surfaces.objects.MoonSurfaceObjects

class Illuminator: Looker {
    override fun getSeenObjects(): String {
        return MoonSurfaceObjects.values().map { it.toString() }.reduce {acc, it -> "$acc, $it" }
    }

    override fun toString(): String {
        return "Иллюминатор"
    }
}