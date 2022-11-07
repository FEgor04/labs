package org.itmo.lab3.characters

import org.itmo.lab3.look.context.LookContext
import org.itmo.lab3.look.lookable.Lookable
import org.itmo.lab3.look.looker.Looker

abstract class Character(private var relief: Double) {
    abstract fun getName(): String

    fun getUp() {
        println("${getName()} поднялся под потолок кабины")
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