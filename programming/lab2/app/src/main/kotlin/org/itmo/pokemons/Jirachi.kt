package org.itmo.pokemons

import org.itmo.moves.special.DazzlingGleam
import org.itmo.moves.status.CalmMind
import org.itmo.moves.status.Refresh
import org.itmo.moves.status.ThunderWave
import ru.ifmo.se.pokemon.Pokemon
import ru.ifmo.se.pokemon.Type

class Jirachi(name: String, level: Int) : Pokemon(name, level) {
    init {
        setType(Type.STEEL, Type.PSYCHIC)
        setMove(CalmMind(), ThunderWave(), DazzlingGleam(), Refresh())
        setStats(100.0, 100.0, 100.0, 100.0, 100.0, 100.0)
    }
}