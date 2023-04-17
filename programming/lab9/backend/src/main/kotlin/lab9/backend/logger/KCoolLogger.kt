package lab9.backend.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

public fun <R: Any> R.KCoolLogger(): Lazy<Logger> {
    val name = this::class.qualifiedName
    if(this::class.isCompanion) {
        return lazy { LoggerFactory.getLogger(name?.dropLast(".Companion".length)) }
    }
    return lazy { LoggerFactory.getLogger(name) }
}