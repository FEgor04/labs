package org.itmo.lab4.look.looker

import org.itmo.lab4.look.lookable.Lookable

/**
 * Интерфейс, через который можно посмотреть на Lookable
 */
interface Looker {
    /**
     * Возвращает объекты, которыен были увидены в виде строки для вывода
     */
    fun getSeenObjects(lookable: Lookable): String
}