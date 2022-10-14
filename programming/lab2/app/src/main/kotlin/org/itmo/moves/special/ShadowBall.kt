package org.itmo.moves.special

import ru.ifmo.se.pokemon.*

class ShadowBall : SpecialMove(Type.GHOST, 80.0, 100.0) {
    override fun applyOppEffects(p0: Pokemon) {
        p0.addEffect(Effect().chance(0.2).stat(Stat.SPECIAL_DEFENSE, -1).turns(-1))
    }

    override fun checkAccuracy(p0: Pokemon?, p1: Pokemon?): Boolean {
        return true
    }

    override fun describe(): String {
        return "использует Shadow Ball"
    }
}