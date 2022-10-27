package org.itmo.lab3.planets

import org.itmo.lab3.look.lookable.Lookable
import org.itmo.lab3.planets.surfaces.Surface

interface Planet: Lookable {
 fun getName(): String
 fun getSurface(): Surface
}

