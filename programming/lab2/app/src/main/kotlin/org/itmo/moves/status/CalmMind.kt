package org.itmo.moves.status

import ru.ifmo.se.pokemon.*

class CalmMind() : StatusMove(Type.PSYCHIC, 0.0, 100.0) {

    override fun applySelfEffects(self: Pokemon) {
        self.addEffect(Effect().stat(Stat.SPECIAL_ATTACK, 1).stat(Stat.SPECIAL_DEFENSE, 1))
    }

    override fun describe(): String {
        return "использует Calm Mind"
    }
}