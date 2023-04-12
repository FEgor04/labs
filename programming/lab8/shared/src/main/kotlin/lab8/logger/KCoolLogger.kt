package lab8.logger

import org.apache.logging.log4j.kotlin.KotlinLogger
import kotlin.reflect.KProperty

/**
 * Логгер на основе библиотеки Log4j2 и механизма делегатов в Kotlin
 */
class KCoolLogger {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): KotlinLogger {
        val name = (thisRef?.javaClass?.name ?: "null").replace("\$Companion", "")
        return org.apache.logging.log4j.kotlin.logger(name)
    }
}
