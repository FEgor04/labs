package org.itmo.lab3.look.looker

/**
 * Интерфейс, через который можно посмотреть на Lookable
 */
interface Looker {
    /**
     * Возвращает объекты, которыен были увидены в виде строки для вывода
     */
    fun getSeenObjects(): String
}