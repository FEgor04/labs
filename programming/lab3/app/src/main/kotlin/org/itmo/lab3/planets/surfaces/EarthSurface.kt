package org.itmo.lab3.planets.surfaces

/**
 * Класс поверхности Земли
 * В сказке о ней ничего не сказано, будем считать что она пустая
 */
class EarthSurface: Surface {
    override fun getObjects(): Array<Any> {
        return arrayOf()
    }

    fun describe(): String {
        return "С корабля ничего не видно =("
    }
}