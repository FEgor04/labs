package org.itmo.lab4.look.looker

import org.itmo.lab4.look.lookable.Lookable
import org.itmo.lab4.planets.Moon

class Telescope: Looker {
    override fun getSeenObjects(lookable: Lookable): String {
        if(lookable is Moon) {
            return "Просто дефолтная Луна"
        }
        return "wtf"
    }

    override fun toString(): String {
        return "телескоп"
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if(other is Looker) {
            return other.toString() == this.toString()
        }
        return false
    }
}