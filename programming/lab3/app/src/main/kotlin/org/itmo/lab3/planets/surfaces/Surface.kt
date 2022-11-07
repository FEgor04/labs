package org.itmo.lab3.planets.surfaces

/**
 * Интерфейс поверхности планеты
 */
interface Surface {
    /**
     * Возвращает объекты на поверхности
     */
    fun getObjects(): List<Any>
}