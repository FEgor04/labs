package org.itmo.lab3.characters

import org.itmo.lab3.look.context.LookContext
import org.itmo.lab3.look.lookable.Lookable
import org.itmo.lab3.look.looker.Looker

abstract class Character() {
    abstract fun getName(): String

    fun getUp() {
        println("${getName()} поднялся вверх")
    }

    fun look(looker: Looker, lookable: Lookable): LookContext {
        val context = LookContext.New(looker, lookable)
        println("${getName()} смотрит в $looker на $lookable и видит ${looker.getSeenObjects()}. Качество: ${context.getQuality()}")
        return context
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
        return getName().hashCode() shl 1
    }
}