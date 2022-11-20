package org.itmo.lab4.spaceships

import org.itmo.lab4.characters.Character
import org.itmo.lab4.look.looker.Looker
import org.itmo.lab4.planets.Planet
import org.itmo.lab4.spaceships.rocket.engine.IEngine

interface Spaceship {
    fun calcApproachSpeed(planet: Planet): Double
    fun getIlluminator(): Looker
    fun getDistantToPlanet(planet: Planet): Double
    fun getEngine(): IEngine
    fun getCrew(): Crew
    interface Crew: List<Character> {
        fun stun()
        fun getMember(id: Int): Character
        fun <R>map(transform: (Character) -> R): List<R>
    }
}