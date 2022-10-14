package org.itmo.pokemons

import org.itmo.moves.status.SwordsDance

class Leavanny(name: String, level: Int) : Swadloon(name, level) {
    init {
        addMove(SwordsDance())
        setStats(75.0, 103.0, 80.0, 70.0, 80.0, 92.0)
    }
}