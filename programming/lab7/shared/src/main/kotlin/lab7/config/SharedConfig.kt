package lab7.config

import java.nio.charset.Charset

object SharedConfig {
    const val CHUNK_SIZE = 62 * 1024 // 62 KiB size
    const val SERVER_PORT = 1234
    const val LOAD_BALANCER_PORT = 5555
    const val UDP_HAS_NEXT_CHUNK: Byte = Byte.MAX_VALUE
    const val UDP_FINAL_CHUNK: Byte = 42
    const val BYTES_IN_MEGABYTE = 1024 * 1024
    val ACK_BYTES: ByteArray = "ACK".toByteArray(Charset.forName("UTF-8"))
}
