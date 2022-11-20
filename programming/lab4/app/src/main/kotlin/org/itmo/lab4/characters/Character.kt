package org.itmo.lab4.characters

import org.itmo.lab4.exceptions.CantSleepException
import org.itmo.lab4.look.context.LookContext
import org.itmo.lab4.look.lookable.Lookable
import org.itmo.lab4.look.looker.Looker
import org.itmo.lab4.scare.Scareable
import org.itmo.lab4.scare.Scary
import org.itmo.lab4.sound.Listener
import org.itmo.lab4.sound.Sounding

abstract class Character(private var relief: Double): Listener, Scareable {
    abstract fun getName(): String
    private var status: Status = Status.OK
    protected open val face: Face = object: Face() {}

    /**
     * Так как класс inner, то у него есть доступ к полям внешнего класса Character
     * В Java он был бы non-static nested class
     */
    abstract inner class Face {
        open fun showScaredEmotion() {
            println("На лице ${getName()} виден страх")
        }
    }

    fun getUp() {
        println("${getName()} понядялся под потолок кабины")
    }

    fun getDown() {
        println("${getName()} опустился на дно кабины")
    }

    fun say(message: String) {
        println("${getName()} сказал: \"$message\"")
    }

    fun beLulled() {
        status = Status.LULLED
    }

    override fun getScare(): Boolean {
        return status == Status.SCARED
    }

    override fun beScared(cause: String) {
        status=Status.SCARED
        println("${getName()} был напуган. Прична: $cause")
        face.showScaredEmotion()
    }

    fun goToCabin() {
        println("${getName()} поднялся в астрономическую кабину")
    }

    @Throws(CantSleepException::class)
    fun tryToSleep() {
        if(status==Status.LULLED) {
            println("${getName()} уснул")
            status=Status.SLEEPING
        }
        else {
            throw CantSleepException("мешает двигатель");
        }
    }

    override fun listenTo(sounding: Sounding) {
        println("${getName()} слушает $sounding. Звук: ${sounding.makeSound()}")
    }

    fun bother(character: Character) {
        if(character != this) {
            println("${getName()} тормошит персонажа ${character.getName()}")
            character.wakeUp(getName())
        }
    }

    fun wakeUp(cause: String) {
        status=Status.OK
        println("${getName()} проснулся. Причина: $cause")
    }

    fun look(looker: Looker, lookable: Lookable): LookContext {
        val context = LookContext.New(looker, lookable)
        println("${getName()} смотрит в $looker на $lookable и видит ${context.getSeenObjects()}. Качество: ${context.getQuality()}")
        return context
    }

    /**
     * Прибавляет к relief значение delta и возвращает полученное значение
     */
    fun modifyRelief(delta: Double): Double {
        relief += delta
        println("Облегчение персонажа ${this.getName()} увеличено на $delta и теперь равно $relief")
        return relief
    }

    override fun toString(): String {
        return getName()
    }

    override fun equals(other: Any?): Boolean {
        if(other is Character) {
            return this.getName() == other.getName()
        }
        return false
    }

    override fun hashCode(): Int {
        return getName().hashCode() + 10 * this.relief.hashCode()
    }
}