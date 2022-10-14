package org.itmo.pokemons

import org.itmo.moves.status.Rest
import org.itmo.moves.status.StringShot
import ru.ifmo.se.pokemon.Pokemon
import ru.ifmo.se.pokemon.Type

open class Sewaddle(name: String, level: Int): Pokemon(name, level) {
    init {
        setType(Type.BUG, Type.GRASS)
        setMove(Rest(), StringShot())
        setStats(45.0, 53.0, 70.0, 40.0, 60.0, 42.0)
    }
}