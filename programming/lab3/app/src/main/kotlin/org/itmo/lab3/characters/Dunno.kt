package org.itmo.lab3.characters

/**
 * Класс персонажа Незнайка
 */
class Dunno(relief: Double = 0.0): Character(relief) {
    override fun getName(): String {
        return "Незнайка"
    }
}