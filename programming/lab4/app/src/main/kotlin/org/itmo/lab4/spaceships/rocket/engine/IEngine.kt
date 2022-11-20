package org.itmo.lab4.spaceships.rocket.engine

import org.itmo.lab4.characters.Character
import org.itmo.lab4.sound.Sounding

interface IEngine: Sounding {
    fun lull(character: Character)
}