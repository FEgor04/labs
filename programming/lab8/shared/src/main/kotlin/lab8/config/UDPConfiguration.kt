package lab8.config

interface UDPConfiguration {
    val chunkSize: Int
    val isFinalChunkByte: Byte
    val ackBytes: ByteArray
}
