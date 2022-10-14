package org.itmo.moves.status

import ru.ifmo.se.pokemon.*

class StringShot : StatusMove(Type.BUG, 0.0, 95.0) {
    override fun applyOppEffects(p0: Pokemon) {
        p0.addEffect(Effect().turns(-1).stat(Stat.SPEED, -2))
    }

    override fun describe(): String {
        return "использует String Shot"
    }
}