package lab7.udp

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import lab7.config.SharedConfig
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.util.concurrent.Executors

class BetterDatagramChannelTest : WordSpec({
    val addr = InetSocketAddress(5555)
    val channel = mockk<DatagramChannel>()
    val acknowledger = mockk<AcknowledgeProviderFunc>(relaxed = true)
    var callNumber = 1
    val chunkSize = 4
    val betterChannel =
        BetterDatagramChannel(
            channel,
            SharedConfig.ACK_BYTES,
            chunkSize,
            SharedConfig.UDP_FINAL_CHUNK,
            acknowledgeProvider = acknowledger
        )

    afterEach {
        clearMocks(channel, acknowledger)
    }

    "send" should {
        "send small data" {
            val slot = slot<ByteBuffer>()
            val expected = byteArrayOf(1, 2, 3, 4)
            val buf = ByteBuffer.wrap(expected)
            every {
                channel.send(capture(slot), addr)
            } returns expected.size + 1

            betterChannel.send(buf, addr) shouldBe 5

            for (i in expected.indices) {
                slot.captured[i] shouldBe (expected + SharedConfig.UDP_FINAL_CHUNK)[i]
            }

            verify {
                channel.send(any(), addr)
            }
        }

        "send big data" {
            val chunk1 = byteArrayOf(1, 2, 3, 4)
            val chunk2 = byteArrayOf(5, 6, 7)
            val expected = byteArrayOf(*chunk1, *chunk2)
            val slot = slot<ByteBuffer>()

            every {
                channel.send(capture(slot), addr)
            } answers {
                if (callNumber == 1) {
                    for (i in 0 until 4) {
                        slot.captured[i] shouldBe chunk1[i]
                    }
                    callNumber += 1
                    4
                } else {
                    for (i in 0 until 3) {
                        slot.captured[i] shouldBe chunk2[i]
                    }
                    slot.captured[3] shouldBe SharedConfig.UDP_FINAL_CHUNK
                    4
                }
            }

            betterChannel.send(ByteBuffer.wrap(expected), addr) shouldBe 8

            verify(exactly = 2) { channel.send(any(), addr) }
        }
    }

    "receive" should {
        "receive one chunk" {
            val expected = byteArrayOf(1, 2, 3)
            val slot = slot<ByteBuffer>()
            every {
                channel.receive(capture(slot))
            } answers {
                slot.captured.put(expected + SharedConfig.UDP_FINAL_CHUNK)
                addr
            }

            every {
                channel.send(any(), any())
            } returns 3

            val (actual, actualAddr) = betterChannel.receive()

            actual shouldBe expected
            actualAddr shouldBe addr

            verify { channel.receive(any()) }
        }

        "receive many chunks" {
            val chunk1 = byteArrayOf(1, 2, 3, 4)
            val chunk2 = byteArrayOf(5, 6)
            val expected = byteArrayOf(*chunk1, *chunk2)
            val slot = slot<ByteBuffer>()
            var callNumber = 0
            every {
                channel.receive(capture(slot))
            } answers {
                if (callNumber == 0) {
                    slot.captured.put(chunk1)
                    callNumber++
                } else {
                    slot.captured.put(chunk2 + SharedConfig.UDP_FINAL_CHUNK)
                }
                addr
            }
            every {
                channel.send(any(), any())
            } returns 4

            val (actual, actualAddr) = betterChannel.receive()
            actual shouldBe expected
            actualAddr shouldBe addr

            verify(exactly = 2) {
                channel.receive(any())
            }
            verify(exactly = 1) {
                channel.send(any(), any())
            }
        }
    }

    "send and recieve to/from another channel" should {
        val channel1 = DatagramChannel.open().bind(null)
        val channel2 = DatagramChannel.open().bind(null)

        afterTest {
        }

        val chunkSize = 1000

        val bc1 = BetterDatagramChannel(
            channel1,
            SharedConfig.ACK_BYTES,
            chunkSize,
            SharedConfig.UDP_FINAL_CHUNK,
            totalBufferSizeMultiplier = 3000,
        )
        val bc2 = BetterDatagramChannel(
            channel2,
            SharedConfig.ACK_BYTES,
            chunkSize,
            SharedConfig.UDP_FINAL_CHUNK,
            totalBufferSizeMultiplier = 3000
        )

        "send one chunk" {
            val dataSize = 500
            val data = (1..dataSize).map { it.toByte() }.toByteArray()
            val job1 = async { bc1.send(ByteBuffer.wrap(data), channel2.localAddress) }
            val job2 = async { bc2.receive() }
            val bytesSent = job1.await()
            val (actual, addr) = job2.await()
            (addr as InetSocketAddress).port shouldBe (channel1.localAddress as InetSocketAddress).port
            bytesSent shouldBe dataSize + 1
            actual.size shouldBe data.size
            actual.toList().forEachIndexed { id, _ ->
                actual[id] shouldBe data[id]
            }
        }

        "send many chunks" {
            val chunksNumber = 5
            val dataSize = chunkSize * chunksNumber - 100
            val data = (1..dataSize).map { it.toByte() }.toByteArray()
            val sendPool = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
            val receivePool = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
            val job2 = async(receivePool) { bc2.receive() }
            val job1 = async(sendPool) { bc1.send(ByteBuffer.wrap(data), channel2.localAddress) }
            val bytesSent = job1.await()
            val (actual, addr) = job2.await()
            (addr as InetSocketAddress).port shouldBe (channel1.localAddress as InetSocketAddress).port
            bytesSent shouldBe dataSize + 1
            actual.size shouldBe data.size
            actual.toList().forEachIndexed { id, _ ->
                actual[id] shouldBe data[id]
            }
        }

        "send 2000 chunks" {
            val chunksNumber = 2000
            val sendPool = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
            val receivePool = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
            val dataSize = chunkSize * chunksNumber - 100
            val data = (1..dataSize).map { it.toByte() }.toByteArray()
            val receiveJob = async(receivePool) { bc2.receive() }
            val sendJob = async(sendPool) { bc1.send(ByteBuffer.wrap(data), channel2.localAddress) }
            val bytesSent = sendJob.await()
            val (actual, address) = receiveJob.await()
            (address as InetSocketAddress).port shouldBe (channel1.localAddress as InetSocketAddress).port
            bytesSent shouldBe dataSize + 1
            actual.size shouldBe data.size
            actual.toList().forEachIndexed { id, _ ->
                actual[id] shouldBe data[id]
            }
        }
    }
})