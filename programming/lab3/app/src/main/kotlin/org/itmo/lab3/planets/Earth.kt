package org.itmo.lab3.planets

import org.itmo.lab3.look.looker.Telescope
import org.itmo.lab3.planets.surfaces.EarthSurface
import org.itmo.lab3.planets.surfaces.Surface

// а вот Земля точно планета
class Earth: Planet {
    var telescope: Telescope = Telescope()

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