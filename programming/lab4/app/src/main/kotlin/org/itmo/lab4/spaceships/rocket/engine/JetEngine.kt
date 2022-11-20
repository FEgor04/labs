package org.itmo.lab4.spaceships.rocket.engine

import org.itmo.lab4.characters.Character

class JetEngine: IEngine {
    override fun makeSound(): String {
        return "Чаф-чаф-чаф"
    }

    override fun lull(character: Character) {
        println("${toString()} убаюкивает $character")
        character.beLulled()
    }

    override fun toString(): String {
        return "реактивный двигатель"
    }

    override fun hashCode(): Int {
        return "реактивный двигатель".hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return (other is JetEngine)
    }
}