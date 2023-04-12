package lab8.udp

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import lab8.udp.communicator.BetterDatagramChannel
import lab8.udp.communicator.BetterDatagramSocket
import java.net.DatagramSocket
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.nio.charset.Charset
import java.util.concurrent.Executors

class BetterDatagramChannelSocketCommunicationTest : WordSpec({
    val ackBytes = "ACK".toByteArray(Charset.forName("UTF-8"))
    val udpFinalChunk: Byte = 42

    "DatagramSocket" should {
        val s = DatagramSocket()
        val c = DatagramChannel.open().bind(null)
        val bs = BetterDatagramSocket(
            s,
            ackBytes,
            1000,
            udpFinalChunk,
        )
        val bc = BetterDatagramChannel(
            c,
            ackBytes,
            1000,
            udpFinalChunk,
            totalBufferSizeMultiplier = 2100
        )

        "send one chunk" {
            val dataSize = 1000
            val data = (1..dataSize).map { it.toByte() }.toByteArray()
            val job1 = async { bs.send(data, c.localAddress) }
            val job2 = async { bc.receive() }
            job1.await()
            val (actual, address) = job2.await()
            actual.size shouldBe data.size
            actual shouldBe data
        }

        "send many chunks" {
            val sendPool = Executors.newFixedThreadPool(4).asCoroutineDispatcher()
            val receivePool = Executors.newFixedThreadPool(4).asCoroutineDispatcher()
            checkAll(Arb.int(10, 100), Arb.int(0, 10)) { chunksNumber, offset ->
                println("NEW TEST!")
                val dataSize = 1000 * chunksNumber - offset
                val data = (1..dataSize).map { it.toByte() }.toByteArray()
                val job1 = async(sendPool) { bs.send(data, c.localAddress) }
                val job2 = async(receivePool) { bc.receive() }
                job1.await()
                val (actual, address) = job2.await()
                actual.size shouldBe data.size
                actual shouldBe data
            }
        }

        s.close()
        c.close()
    }

    "DatagramChannel" should {
        val s = DatagramSocket()
        val c = DatagramChannel.open().bind(null)
        val bs = BetterDatagramSocket(
            s,
            ackBytes,
            1000,
            udpFinalChunk,
        )
        val bc = BetterDatagramChannel(
            c,
            ackBytes,
            1000,
            udpFinalChunk,
            totalBufferSizeMultiplier = 2100
        )

        "send chunks" {
            val sendPool = Executors.newFixedThreadPool(4).asCoroutineDispatcher()
            val receivePool = Executors.newFixedThreadPool(4).asCoroutineDispatcher()
            checkAll(Arb.int(1, 100), Arb.int(0, 10)) { chunksNumber, offset ->
                println("NEW TEST!: Chunks: $chunksNumber Offset: $offset")
                val dataSize = 1000 * chunksNumber - offset
                val data = (1..dataSize).map { it.toByte() }.toByteArray()
                val job1 = async(sendPool) { bc.send(ByteBuffer.wrap(data), s.localSocketAddress) }
                val job2 = async(receivePool) { bs.receive() }
                job1.await()
                val (actual, address) = job2.await()
                actual.size shouldBe data.size
                actual shouldBe data
            }
        }

        s.close()
        c.close()
    }
})
