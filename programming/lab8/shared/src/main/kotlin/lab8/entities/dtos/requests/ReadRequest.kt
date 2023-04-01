package lab8.entities.dtos.requests

/**
 * Запрос на чтение данных из коллекции.
 * Может обрабатываться как сервером, так и балансировщиком (кэшем)
 */
sealed class ReadRequest : RequestDTO()
