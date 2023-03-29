package lab7.entities.dtos.responses

import kotlinx.serialization.Serializable

/**
 * Результат команды ReplaceIfLower
 */
@Serializable
enum class ReplaceIfLowerResults {
    NOT_EXISTS,
    REPLACED,
    NOT_REPLACED,
    BAD_AUTHOR,
}
