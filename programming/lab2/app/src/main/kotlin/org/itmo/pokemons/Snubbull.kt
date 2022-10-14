package org.itmo.pokemons

import org.itmo.moves.special.ShadowBall
import org.itmo.moves.status.Charm
import org.itmo.moves.status.ThunderWave
import ru.ifmo.se.pokemon.Pokemon
import ru.ifmo.se.pokemon.Type

open class Snubbull(name: String, level: Int) : Pokemon(name, level) {
    init {
        setType(Type.FAIRY)
        setMove(ThunderWave(), ShadowBall(), Charm())
        setStats(60.0, 80.0, 50.0, 40.0, 40.0, 30.0)
    }
}