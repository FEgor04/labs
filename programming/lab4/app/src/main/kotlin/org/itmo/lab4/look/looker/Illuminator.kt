package org.itmo.lab4.look.looker

import org.itmo.lab4.look.lookable.Lookable
import org.itmo.lab4.planets.Moon

class Illuminator: Looker {
    override fun getSeenObjects(lookable: Lookable): String {
        if(lookable is Moon) {
            return lookable.getSurface().toString()
        }
        return "wft"
    }

    override fun toString(): String {
        return "Иллюминатор"
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