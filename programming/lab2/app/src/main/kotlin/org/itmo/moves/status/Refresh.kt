package org.itmo.moves.status

import ru.ifmo.se.pokemon.*

class Refresh : StatusMove(Type.NORMAL, 0.0, 100.0) {
    override fun applySelfEffects(p0: Pokemon) {
        if (p0.condition in arrayOf(Status.PARALYZE, Status.POISON, Status.BURN)) {
            p0.setCondition(Effect().condition(Status.NORMAL))
        }
    }

    override fun checkAccuracy(p0: Pokemon?, p1: Pokemon?): Boolean {
        return true
    }

    override fun describe(): String {
        return "использует Refresh"
    }
}