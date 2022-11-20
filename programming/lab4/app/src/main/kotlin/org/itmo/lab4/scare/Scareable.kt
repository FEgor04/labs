package org.itmo.lab4.scare

/**
 * Интерфейс, который может испугаться чего-то
 */
interface Scareable {
    fun getScare(): Boolean
    fun beScared(cause: String)
}