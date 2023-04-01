package lab8.server.data.udp

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import lab8.exceptions.ServerException
import lab8.udp.acknowledge.FlowAcknowledgeProvider
import lab8.udp.communicator.BetterDatagramSocket
import java.net.InetSocketAddress

class DatagramProducerTest : WordSpec({
    val socket = mockk<BetterDatagramSocket>()
    val acknowledger = mockk<FlowAcknowledgeProvider>()
    val addr = InetSocketAddress(1234)
    val ackByteArray = "ACK".toByteArray()

    val producer = DatagramProducer(socket, acknowledger)

    afterEach { clearMocks(socket, acknowledger) }

    "take" should {
        "throw exception on ACK" {
            coEvery { socket.receive() } returns Pair(ackByteArray, addr)
            coEvery { acknowledger.acknowledge(addr) } returns Unit
            shouldThrow<ServerException.AcknowledgePseudoException> { producer.take() }

            coVerify { socket.receive(); acknowledger.acknowledge(addr) }
        }
    }
})
