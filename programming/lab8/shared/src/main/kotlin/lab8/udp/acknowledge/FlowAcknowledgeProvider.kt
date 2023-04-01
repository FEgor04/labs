package lab8.udp.acknowledge

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import lab8.exceptions.ServerException
import lab8.logger.KCoolLogger
import java.net.SocketAddress
import java.time.LocalTime

class FlowAcknowledgeProvider private constructor(
    private val flow: MutableSharedFlow<Pair<LocalTime, SocketAddress>>,
) : AcknowledgeProvider {
    private val sharedFlow: SharedFlow<Pair<LocalTime, SocketAddress>>
        get() {
            return flow.asSharedFlow()
        }

    override suspend fun acknowledge(addr: SocketAddress) {
        logger.info("Got ACK for $addr")
        flow.emit(Pair(LocalTime.now(), addr))
    }

    override suspend fun waitForAcknowledge(addr: SocketAddress) {
        val start = LocalTime.now()
        try {
            sharedFlow
                .filter { it.first > start && it.second == addr }
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
