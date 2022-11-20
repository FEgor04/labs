package org.itmo.lab4.sound

/**
 * Интерфейс, слущающий звук
 */
interface Listener {
    fun listenTo(sounding: Sounding)
}