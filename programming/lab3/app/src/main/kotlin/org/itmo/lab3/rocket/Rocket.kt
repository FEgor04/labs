package org.itmo.lab3.rocket

import org.itmo.lab3.characters.Character
import org.itmo.lab3.look.looker.Illuminator
import org.itmo.lab3.planets.Planet

/**
 * Класс ракеты, на которой летят персонажи
 * @property crew экипапж ракеты
 * @property speed скорость ракеты
 */
class Rocket(val crew: Array<Character>, val speed: Double) {
    final val illuminator: Illuminator = Illuminator()

    fun calcApproachSpeed(planet: Planet) {

    }

}