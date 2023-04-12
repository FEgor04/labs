package lab8.entities.dtos.requests

import kotlinx.serialization.Serializable

/**
 * Запрос на регистрацию нового сервера в балансировщике.
 * Должен отправляться только сервером.
 */
@Serializable
sealed class BalanceRequest : RequestDTO()
