package org.itmo.moves.status

import ru.ifmo.se.pokemon.*

class Rest: StatusMove(Type.PSYCHIC, 0.0, 100.0) {
    override fun checkAccuracy(p0: Pokemon?, p1: Pokemon?): Boolean {
        return true
    }

    override fun applySelfEffects(p0: Pokemon) {
        p0.setMod(Stat.HP, -(p0.getStat(Stat.HP) - p0.hp).toInt())
        p0.addEffect(Effect().turns(2).condition(Status.SLEEP))
    }

    override fun describe(): String {
        return "пошел поспать)"
    }
}