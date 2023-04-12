package lab8.udp

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import lab8.udp.communicator.BetterDatagramSocket
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.nio.charset.Charset
import java.util.concurrent.Executors

class BetterDatagramSocketTest : WordSpec({
    val ackBytes = "ACK".toByteArray(Charset.forName("UTF-8"))
    val udpFinalChunk: Byte = 42

    val socket = mockk<DatagramSocket>()
    val betterSocket = BetterDatagramSocket(
        socket,
        ackBytes,
        4,
        udpFinalChunk,
    )
    val addr = InetSocketAddress(5555)

    afterTest {
        clearMocks(socket)
    }

    "send" should {
        "send one chunk" {
            val data = byteArrayOf(1, 2, 3)
            val slot = slot<DatagramPacket>()

            every {
                socket.send(capture(slot))
            } returns Unit

            every {
                socket.receive(any())
            } returns Unit

            betterSocket.send(data, addr)
            slot.captured.data shouldBe (data + udpFinalChunk)
            slot.captured.socketAddress shouldBe addr

            verify { socket.send(any()) }
        }

        "send many chunks" {
            val chunk1 = byteArrayOf(1, 2, 3, 4)
            val chunk2 = byteArrayOf(5, 6, 7)
            val expected = byteArrayOf(*chunk1, *chunk2)
            val slot = slot<DatagramPacket>()
            var callNumber = 0

            every {
                socket.receive(any())
            } returns Unit

            every {
                socket.send(capture(slot))
            } answers {
                slot.captured.socketAddress shouldBe addr
                if (callNumber == 0) {
                    slot.captured.data shouldBe chunk1
                    callNumber++
                } else {
                    slot.captured.data shouldBe chunk2 + udpFinalChunk
                }
            }

            betterSocket.send(expected, addr)
            verify(exactly = 2) { socket.send(any()) }
        }
    }

    "receive" should {
        "receive one chunk" {
            val data = byteArrayOf(1, 2, 3)
            val slot = slot<DatagramPacket>()
            every {
                socket.receive(capture(slot))
            } answers {
                slot.captured.setData(data + udpFinalChunk)
                slot.captured.socketAddress = addr
            }

            val (actual, actualAddr) = betterSocket.receive()
            actual shouldBe data
            actualAddr shouldBe addr

            verify { socket.receive(any()) }
        }

        "receive many chunks" {
            val chunk1 = byteArrayOf(1, 2, 3, 4)
            val chunk2 = byteArrayOf(5, 6, 7)
            val expected = byteArrayOf(*chunk1, *chunk2)
            var callNumber = 0
            val slot = slot<DatagramPacket>()
            every {
                socket.receive(capture(slot))
            } answers {
                if (callNumber == 0) {
                    slot.captured.data = chunk1
                    callNumber++
                } else {
                    slot.captured.data = chunk2 + udpFinalChunk
                }
                slot.captured.socketAddress = addr
            }
            every { socket.send(any()) } returns Unit

            val (actual, actualAddr) = betterSocket.receive()
            actual shouldBe expected
            actualAddr shouldBe addr

            verify(exactly = 2) { socket.receive(any()) }
            verify(exactly = 1) { socket.send(any()) }
        }
    }

    "send and receive data to / from another socket" should {
        val s1 = DatagramSocket()
        val s2 = DatagramSocket()

        afterProject {
            s1.close()
            s2.close()
        }

        val bs1 = BetterDatagramSocket(
            s1,
            ackBytes,
            1000,
            udpFinalChunk,
        )
        val bs2 = BetterDatagramSocket(
            s2,
            ackBytes,
            1000,
            udpFinalChunk,
        )

        "send one chunk" {
            val dataSize = 500
            val data = (1..dataSize).map { it.toByte() }.toByteArray()
            val job1 = async { bs1.send(data, s2.localSocketAddress) }
            val job2 = async { bs2.receive() }
            job1.await()
            val (actual, addr) = job2.await()
            (addr as InetSocketAddress).port shouldBe (s1.localSocketAddress as InetSocketAddress).port
            actual.size shouldBe data.size
            actual.toList().forEachIndexed { id, _ ->
                actual[id] shouldBe data[id]
            }
        }

        "send many chunks" {
            val chunkSize = 1000
            val chunksNumber = 5
            val dataSize = chunkSize * chunksNumber - 100
            val data = (1..dataSize).map { it.toByte() }.toByteArray()
            val sendPool = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
            val receivePool = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
            val job2 = async(receivePool) { bs2.receive() }
            val job1 = async(sendPool) { bs1.send(data, s2.localSocketAddress) }
            job1.await()
            val (actual, addr) = job2.await()
            (addr as InetSocketAddress).port shouldBe (s1.localSocketAddress as InetSocketAddress).port
            actual.size shouldBe data.size
            actual.toList().forEachIndexed { id, _ ->
                actual[id] shouldBe data[id]
            }
        }

        "send 2000 chunks" {
            val chunkSize = 1000
            val chunksNumber = 2000
            val dataSize = chunkSize * chunksNumber - 100
            val data = (1..dataSize).map { it.toByte() }.toByteArray()
            val sendPool = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
            val receivePool = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
            val job2 = async(receivePool) { bs2.receive() }
            val job1 = async(sendPool) { bs1.send(data, s2.localSocketAddress) }
            job1.await()
            val (actual, addr) = job2.await()
            (addr as InetSocketAddress).port shouldBe (s1.localSocketAddress as InetSocketAddress).port
            actual.size shouldBe data.size
            actual.toList().forEachIndexed { id, _ ->
                actual[id] shouldBe data[id]
            }
        }
    }
})
