package lab8.udp.acknowledge

import java.net.SocketAddress

interface AcknowledgeProvider {
    suspend fun acknowledge(addr: SocketAddress)
    suspend fun waitForAcknowledge(addr: SocketAddress)
}
