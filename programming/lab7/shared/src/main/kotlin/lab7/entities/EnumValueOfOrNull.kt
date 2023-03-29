package lab7.entities

/**
 * Возвращает enum с указанным значением или null, если такого нет.
 */
@Suppress("SwallowedException")
inline fun <reified T : Enum<T>> enumValueOfOrNull(value: String?): T? {
    if (value == null) {
        return null
    }
    return try {
        enumValueOf<T>(value)
    } catch (e: IllegalArgumentException) {
        null
    }
}
