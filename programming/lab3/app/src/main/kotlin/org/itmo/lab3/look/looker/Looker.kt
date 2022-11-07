package org.itmo.lab3.look.looker

import org.itmo.lab3.look.lookable.Lookable

/**
 * Интерфейс, через который можно посмотреть на Lookable
 */
interface Looker {
    /**
     * Возвращает объекты, которыен были увидены в виде строки для вывода
     */
    fun getSeenObjects(lookable: Lookable): String
}