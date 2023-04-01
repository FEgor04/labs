package lab8.udp.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
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
    for (req in requests) {
        val response = executor.handle(req.first, req.second)
        outputChannel.send(response)
    }
}
