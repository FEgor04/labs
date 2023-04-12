package lab8.client.data

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import lab8.client.config.ClientConfiguration
import lab8.client.domain.RemoteSynchronizer
import lab8.config.UDPConfiguration
import lab8.entities.events.CollectionEvent
import lab8.entities.synchronizer.SynchronizerRequest
import lab8.logger.KCoolLogger
import lab8.udp.acknowledge.FlowAcknowledgeProvider
import lab8.udp.communicator.BetterDatagramSocket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.nio.charset.Charset
import kotlin.concurrent.thread

class UDPSynchronizer(config: ClientConfiguration) : RemoteSynchronizer {
    private val syncAddr = InetSocketAddress("localhost", config.synchronizerPort)
    private val channel = MutableSharedFlow<CollectionEvent>()
    private val socket = BetterDatagramSocket(
        DatagramSocket(),
        config.udpConfig.ackBytes,
        config.udpConfig.chunkSize,
        config.udpConfig.isFinalChunkByte,
        FlowAcknowledgeProvider.generate()
    )
    private val logger by KCoolLogger()

    private suspend fun register() {
        val request = SynchronizerRequest.Subscribe()
        val requestBytes = Json.encodeToString<SynchronizerRequest>(request).toByteArray(Charset.forName("UTF-8"))
        logger.info("registering to synchronizer")
        socket.send(addr = syncAddr, data = requestBytes)
        logger.info("Sent data. Waiting for response")
        socket.receive()
    }

    private suspend fun handleRequest() {
        delay(100)
        logger.info("Waiting for updates from server")
        val (data, _) = socket.receive()
        logger.info("Got data from sync. Decoding to json")
        val event: CollectionEvent = try {
            Json.decodeFromString(data.toString(Charset.forName("UTF-8")))
        } catch (e: Exception) {
            logger.error("Could not decode from json: $e")
            return
        }
        logger.info("Got update from sync: ${event}")
        channel.emit(event)
    }

    override fun subscribe(): SharedFlow<CollectionEvent> {
        return channel.asSharedFlow()
    }

    override suspend fun unsubscribe() {
        val request = SynchronizerRequest.Unsubscribe()
        val requestBytes = Json.encodeToString<SynchronizerRequest>(request).toByteArray(Charset.forName("UTF-8"))
        socket.send(addr = syncAddr, data = requestBytes)
        th.stop()
    }

    val th = thread(isDaemon = true, name = "synchronizer", start=false) {
            runBlocking {
                register()
                launch {
                    while (true) {
                        handleRequest()
                    }
                }
            }
        }

    init {
        th.start()
    }
}