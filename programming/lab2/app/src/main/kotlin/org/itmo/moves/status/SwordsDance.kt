package org.itmo.moves.status

import ru.ifmo.se.pokemon.*

class SwordsDance : StatusMove(Type.NORMAL, 0.0, .0) {
    override fun applySelfEffects(p0: Pokemon) {
        p0.addEffect(Effect().stat(Stat.ATTACK, 2))
    }
}