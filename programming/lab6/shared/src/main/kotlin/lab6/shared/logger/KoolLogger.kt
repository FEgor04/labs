package lab6.shared.logger

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.LoggerConfig
import org.apache.logging.log4j.kotlin.KotlinLogger
import kotlin.reflect.KProperty

/**
 * Крутой логгер на основне библиотеки KotlinLogging
 * https://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin
 */
fun <R: Any> R.koolLogger(): KotlinLogger {
    val name = this.javaClass.name.replace("\$Companion", "")
    return org.apache.logging.log4j.kotlin.logger(name)
}

class KCoolLoggerDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): KotlinLogger {
        val name = (thisRef?.javaClass?.name ?: "null").replace("\$Companion", "")
        return org.apache.logging.log4j.kotlin.logger(name)
    }
}