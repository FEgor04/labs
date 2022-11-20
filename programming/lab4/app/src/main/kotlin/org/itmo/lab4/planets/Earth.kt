package org.itmo.lab4.planets

import org.itmo.lab4.look.looker.Telescope
import org.itmo.lab4.planets.surface.Surface

// а вот Земля точно планета
class Earth: Planet {
    var telescope: Telescope = Telescope()

    class EarthSurface: Surface {
        override fun getObjects(): List<Any> {
            return listOf()
        }

        override fun toString(): String {
            return "С корабля ничего не видно =("
        }

        override fun hashCode(): Int {
            return toString().hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if(other is EarthSurface) {
                return toString() == other.toString()
            }
            return false
        }
    }

    override fun getName(): String {
        return "Земля"
    }

    override fun getSurface(): Surface {
        return EarthSurface();
    }

    override fun toString(): String {
        return getName()
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if(other is Earth) {
            return toString() == other.toString()
        }
        return false
    }
}