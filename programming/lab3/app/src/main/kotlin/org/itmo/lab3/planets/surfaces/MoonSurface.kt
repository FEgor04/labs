package org.itmo.lab3.planets.surfaces

import org.itmo.lab3.planets.surfaces.objects.MoonSurfaceObjects


class MoonSurface: Surface {
    override fun getObjects(): Array<Any> {
        return arrayOf(MoonSurfaceObjects.values())
    }

    fun describe(): String {
        return getObjects().map { it.toString() }.reduce {acc, next -> acc + next}
    }
}