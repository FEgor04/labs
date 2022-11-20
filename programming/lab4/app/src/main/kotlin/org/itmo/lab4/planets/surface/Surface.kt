package org.itmo.lab4.planets.surface

/**
 * Интерфейс поверхности планеты
 */
interface Surface {
    /**
     * Возвращает объекты на поверхности
     */
    fun getObjects(): List<Any>
}