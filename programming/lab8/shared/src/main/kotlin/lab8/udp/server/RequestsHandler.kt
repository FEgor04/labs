package lab8.udp.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import lab8.logger.KCoolLogger
import java.net.SocketAddress

/**
 * Класс, обрабатывающий запросы пользователя
 */
interface RequestsHandler<T, V> {
    suspend fun handle(request: T, addr: SocketAddress): Pair<V, SocketAddress>
}

fun <T, V> CoroutineScope.handleRequest(
    executor: RequestsHandler<T, V>,
    requests: ReceiveChannel<Pair<T, SocketAddress>>,
    outputChannel: SendChannel<Pair<V, SocketAddress>>
) = launch {
    val logger by KCoolLogger()
    logger.info("Waiting for requests")
    for (req in requests) {
        logger.info("Handling request from ${req.second}")
        val response = executor.handle(req.first, req.second)
        logger.info("Handled request from ${req.second}")
        outputChannel.send(response)
    }
}
