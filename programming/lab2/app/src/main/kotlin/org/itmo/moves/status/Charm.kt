package org.itmo.moves.status

import ru.ifmo.se.pokemon.*

class Charm : StatusMove(Type.FAIRY, .0, 100.0) {
    override fun applyOppEffects(p0: Pokemon) {
        p0.addEffect(Effect().turns(-1).stat(Stat.ATTACK, -2))
    }

    override fun checkAccuracy(p0: Pokemon?, p1: Pokemon?): Boolean {
        return true
    }

    override fun describe(): String {
        return "использует Charm"
    }
}