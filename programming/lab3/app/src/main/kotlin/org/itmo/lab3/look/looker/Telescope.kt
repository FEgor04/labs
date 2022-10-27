package org.itmo.lab3.look.looker

class Telescope: Looker {
    override fun getSeenObjects(): String {
        return "Просто дефолтная Луна"
    }

    override fun toString(): String {
        return "телескоп"
    }
}