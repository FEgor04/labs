package lab7.server.data.udp

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import lab7.config.SharedConfig
import lab7.exceptions.ServerException
import lab7.udp.BetterDatagramSocket
import lab7.udp.FlowAcknowledgeProvider
import java.net.InetSocketAddress

class DatagramProducerTest : WordSpec({
    val socket = mockk<BetterDatagramSocket>()
    val acknowledger = mockk<FlowAcknowledgeProvider>()
    val addr = InetSocketAddress(1234)
    val ackByteArray = SharedConfig.ACK_BYTES

    val producer = DatagramProducer(socket, acknowledger)

    afterEach { clearMocks(socket, acknowledger) }

    "take" should {
        "throw exception on ACK" {
            coEvery { socket.receive() } returns Pair(ackByteArray, addr)
            coEvery { acknowledger.sendAcknowledge(addr) } returns Unit
            shouldThrow<ServerException.AcknowledgePseudoException> { producer.take() }

            coVerify { socket.receive(); acknowledger.sendAcknowledge(addr) }
        }
    }
})
