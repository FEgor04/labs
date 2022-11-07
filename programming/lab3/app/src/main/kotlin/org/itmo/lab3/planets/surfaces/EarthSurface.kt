package org.itmo.lab3.planets.surfaces

import org.itmo.lab3.planets.Earth

/**
 * Класс поверхности Земли
 * В сказке о ней ничего не сказано, будем считать что она пустая
 */
class EarthSurface: Surface {
    override fun getObjects(): List<Any> {
        return listOf()
    }

    override fun toString(): String {
        return "С корабля ничего не видно =("
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if(other is EarthSurface) {
            return toString() == other.toString()
        }
        return false
    }
}