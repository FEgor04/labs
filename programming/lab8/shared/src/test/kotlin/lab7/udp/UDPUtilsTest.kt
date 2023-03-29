package lab7.udp

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import lab7.config.SharedConfig

class UDPUtilsTest : WordSpec({

    "splitToChunks" should {
        "leave small data the same" {
            val data = byteArrayOf(1, 2, 3, 4)
            val actual = UDPUtils.splitToChunks(data)
            actual.size shouldBe 1
            actual[0] shouldBe data
        }

        "split big data" {
            val data = byteArrayOf(1, 2, 3, 4)
            val actual = UDPUtils.splitToChunks(data, 2)
            val expected = listOf(byteArrayOf(1, 2, SharedConfig.UDP_HAS_NEXT_CHUNK), byteArrayOf(3, 4))
            actual shouldBe expected
        }
    }

    "receiveChunkedData" should {
        "receive one chunk" {
            val chunk = byteArrayOf(1, 2, 3, 4)
            val chunkSize = 5
            val actual = UDPUtils.receiveChunkedData(SharedConfig.UDP_HAS_NEXT_CHUNK, chunkSize) { chunk }
            actual shouldBe chunk
        }

        "receive many chunks" {
            val expected = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            val chunk1 = byteArrayOf(1, 2, 3, 4, SharedConfig.UDP_HAS_NEXT_CHUNK)
            val chunk2 = byteArrayOf(5, 6, 7, 8)
            var numb = 0
            val actual = UDPUtils.receiveChunkedData(SharedConfig.UDP_HAS_NEXT_CHUNK, 4) {
                if (numb == 0) {
                    numb++
                    chunk1
                } else {
                    chunk2
                }
            }
            actual shouldBe expected
        }
    }
})
