package org.itmo.lab3.spaceships

import org.itmo.lab3.characters.Character
import org.itmo.lab3.look.looker.Looker
import org.itmo.lab3.planets.Planet

interface Spaceship {
    fun calcApproachSpeed(planet: Planet): Double
    fun getCrewMember(index: Int): Character
    fun getIlluminator(): Looker
    fun getDistantToPlanet(planet: Planet): Double
}