package org.itmo.pokemons

import org.itmo.moves.physical.RockSlide

class Granbull(name: String, level: Int): Snubbull(name, level) {
    init {
        addMove(RockSlide())
        setStats(90.0, 120.0, 75.0, 60.0, 60.0, 45.0)
    }
}