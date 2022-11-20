package org.itmo.lab4.characters

import org.itmo.lab4.exceptions.CantSleepException
import kotlin.jvm.Throws

/**
 * Класс персонажа Незнайка
 */
class Dunno(relief: Double = 0.0): Character(relief) {
    override fun getName(): String {
        return "Незнайка"
    }

}