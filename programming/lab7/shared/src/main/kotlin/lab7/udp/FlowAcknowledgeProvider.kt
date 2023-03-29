package lab7.udp

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import lab7.exceptions.ServerException
import lab7.logger.KCoolLogger
import java.net.SocketAddress
import java.time.LocalTime

class FlowAcknowledgeProvider private constructor(private val flow: MutableSharedFlow<Pair<LocalTime, SocketAddress>>) {
    private val sharedFlow: SharedFlow<Pair<LocalTime, SocketAddress>>
        get() {
            return flow.asSharedFlow()
        }

    suspend fun sendAcknowledge(addr: SocketAddress) {
        logger.info("Got ACK for $addr")
        flow.emit(Pair(LocalTime.now(), addr))
    }

    suspend fun waitForAck(wanted: SocketAddress) {
        val start = LocalTime.now()
        try {
            sharedFlow
                .filter { it.first > start && it.second == wanted }
                .collect { throw ServerException.AcknowledgePseudoException() }
        } catch (_: ServerException.AcknowledgePseudoException) {
        }
    }

    companion object {
        fun generate(): FlowAcknowledgeProvider {
            return FlowAcknowledgeProvider(MutableSharedFlow(0))
        }

        private val logger by KCoolLogger()
    }
}
