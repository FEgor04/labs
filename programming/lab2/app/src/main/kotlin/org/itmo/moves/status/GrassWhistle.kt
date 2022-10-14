package org.itmo.moves.status

import ru.ifmo.se.pokemon.Effect
import ru.ifmo.se.pokemon.Pokemon
import ru.ifmo.se.pokemon.StatusMove
import ru.ifmo.se.pokemon.Type

class GrassWhistle : StatusMove(Type.GRASS, 0.0, 55.0) {
    override fun applyOppEffects(p0: Pokemon) {
        Effect.sleep(p0)
    }
}