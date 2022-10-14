package org.itmo.moves.special

import ru.ifmo.se.pokemon.Pokemon
import ru.ifmo.se.pokemon.SpecialMove
import ru.ifmo.se.pokemon.Type

class DazzlingGleam : SpecialMove(Type.FAIRY, 80.0, 100.0) {
    override fun checkAccuracy(p0: Pokemon?, p1: Pokemon?): Boolean {
        return true
    }

    override fun describe(): String {
        return "использует Dazzling Gleam"
    }
}