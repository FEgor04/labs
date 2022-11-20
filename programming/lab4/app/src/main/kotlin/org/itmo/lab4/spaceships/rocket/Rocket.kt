package org.itmo.lab4.spaceships.rocket

import org.itmo.lab4.characters.Character
import org.itmo.lab4.exceptions.NoCrewMemberException
import org.itmo.lab4.look.looker.Illuminator
import org.itmo.lab4.look.looker.Looker
import org.itmo.lab4.planets.Planet
import org.itmo.lab4.spaceships.Spaceship
import org.itmo.lab4.spaceships.rocket.engine.IEngine
import org.itmo.lab4.spaceships.rocket.engine.JetEngine

/**
 * Класс ракеты, на которой летят персонажи
 * @property crew экипапж ракеты
 * @property speed скорость ракеты
 */
class Rocket(crew: Array<Character>, private val speed: Double, private val illuminator: Looker = Illuminator(), private val engine: IEngine = JetEngine()): Spaceship {
    /* Вложенный класс. так как он не inner, то Crew не имеет доступа к Rocket => его аналогом в java будет static class */
    private class Crew(private val members: Array<Character>): Spaceship.Crew {
        override fun stun() {
            println("Экипаж был ошеломлен")
        }

        override fun getMember(id: Int): Character {
            if(id >= this.members.size) {
                throw NoCrewMemberException(id)
            }
            return this.members[id]
        }

        override val size: Int
            get() = this.members.size

        override fun get(index: Int): Character {
            return this.members[index]
        }

        override fun isEmpty(): Boolean {
            return this.members.isEmpty()
        }

        override fun iterator(): Iterator<Character> {
           return this.members.iterator()
        }

        override fun listIterator(): ListIterator<Character> {
            return this.members.toList().listIterator()
        }

        override fun listIterator(index: Int): ListIterator<Character> {
            return members.toList().listIterator()
        }

        override fun subList(fromIndex: Int, toIndex: Int): List<Character> {
            return members.toList().subList(fromIndex, toIndex)
        }

        override fun lastIndexOf(element: Character): Int {
            return members.lastIndexOf(element)
        }

        override fun indexOf(element: Character): Int {
           return members.indexOf(element)
        }

        override fun containsAll(elements: Collection<Character>): Boolean {
            for(i in elements) {
                if(!this.contains(i)) {
                    return false
                }
            }
            return true
        }

        override fun contains(element: Character): Boolean {
            return this.members.contains(element)
        }

        override fun <R>map(transform: (Character) -> R): List<R> {
            return this.members.map(transform)
        }

        override fun equals(other: Any?): Boolean {
            if(other is Rocket.Crew) {
                return this.members.contentEquals(other.members)
            }
            return false
        }

        override fun hashCode(): Int {
            return members.contentHashCode()
        }
    }

    private val crew = Crew(crew)

    override fun calcApproachSpeed(planet: Planet): Double {
        return 1500.0
    }

    override fun getEngine(): IEngine {
       return this.engine
    }

    override fun getCrew(): Spaceship.Crew {
        return crew
    }

    override fun getIlluminator(): Looker {
        return this.illuminator
    }

    override fun getDistantToPlanet(planet: Planet): Double {
        return 0.1
    }

    override fun toString(): String {
        return "Ракета{speed=$speed, crew=${crew.map { it.toString() }} illuminator=$illuminator"
    }

    override fun hashCode(): Int {
        return speed.hashCode() + 10 * crew.hashCode() + 100 * illuminator.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if(other is Rocket) {
            return speed == other.speed && crew == other.crew && illuminator == other.illuminator
        }
        return false
    }
}