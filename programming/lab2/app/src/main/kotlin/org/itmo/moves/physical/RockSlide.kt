package org.itmo.moves.physical

import ru.ifmo.se.pokemon.Effect
import ru.ifmo.se.pokemon.PhysicalMove
import ru.ifmo.se.pokemon.Pokemon
import ru.ifmo.se.pokemon.Type

class RockSlide : PhysicalMove(Type.ROCK, 75.0, 90.0) {
    override fun applyOppEffects(p0: Pokemon) {
        if (Math.random() < 0.3) {
            Effect.flinch(p0)
        }
    }

    override fun describe(): String {
        return "использует Rock Slide"
    }
}