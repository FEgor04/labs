package org.itmo.lab3.spaceships.rocket

import org.itmo.lab3.characters.Character
import org.itmo.lab3.look.looker.Illuminator
import org.itmo.lab3.look.looker.Looker
import org.itmo.lab3.planets.Planet
import org.itmo.lab3.spaceships.Spaceship

/**
 * Класс ракеты, на которой летят персонажи
 * @property crew экипапж ракеты
 * @property speed скорость ракеты
 */
class Rocket(private val crew: Array<Character>, private val speed: Double, private val illuminator: Looker = Illuminator()): Spaceship {

    override fun calcApproachSpeed(planet: Planet): Double {
        return 1500.0
    }

    override fun getCrewMember(index: Int): Character {
        return this.crew[index]
    }

    override fun getIlluminator(): Looker {
        return this.illuminator
    }

    override fun getDistantToPlanet(planet: Planet): Double {
        return 0.1
    }

    override fun toString(): String {
        return "Ракета{speed=$speed, crew=${crew.map { it.toString() }} illuminator=$illuminator"
    }

    override fun hashCode(): Int {
        return speed.hashCode() + 10 * crew.hashCode() + 100 * illuminator.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if(other is Rocket) {
            return speed == other.speed && crew.contentEquals(other.crew) && illuminator == other.illuminator
        }
        return false
    }
}