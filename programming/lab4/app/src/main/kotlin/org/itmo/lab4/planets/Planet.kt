package org.itmo.lab4.planets

import org.itmo.lab4.look.lookable.Lookable
import org.itmo.lab4.planets.surface.Surface

interface Planet: Lookable {
 fun getName(): String
 fun getSurface(): Surface
}

