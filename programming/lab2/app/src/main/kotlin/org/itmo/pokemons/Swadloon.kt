package org.itmo.pokemons

import org.itmo.moves.status.GrassWhistle

open class Swadloon(name: String, level: Int): Sewaddle(name, level) {
    init {
        addMove(GrassWhistle())
        setStats(55.0, 63.0, 90.0, 50.0, 80.0, 42.0)
    }
}