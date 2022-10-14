package org.itmo.moves.status

import ru.ifmo.se.pokemon.Effect
import ru.ifmo.se.pokemon.Pokemon
import ru.ifmo.se.pokemon.StatusMove
import ru.ifmo.se.pokemon.Type

class ThunderWave : StatusMove(Type.ELECTRIC, 0.0, 90.0) {
    override fun applyOppEffects(opp: Pokemon?) {
        Effect.paralyze(opp);
    }

    override fun describe(): String {
        return "использует Thunder Wave"
    }
}