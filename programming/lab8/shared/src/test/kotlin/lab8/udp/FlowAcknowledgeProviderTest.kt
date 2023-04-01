package lab8.udp

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.should
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import lab8.udp.acknowledge.FlowAcknowledgeProvider
import java.net.InetSocketAddress
import java.time.LocalTime

class FlowAcknowledgeProviderTest : WordSpec({
    "acknowledger" should {
        "before-ack" { // Сначала ждем, потом отправляем ACK
            val acknowledger = FlowAcknowledgeProvider.generate()
            val addr = InetSocketAddress(1234)
            val started = LocalTime.now()
            val waiter = async { acknowledger.waitForAcknowledge(addr) }
            delay(10)
            acknowledger.acknowledge(addr)
            waiter.await()
            val end = LocalTime.now()
            val millis = (started.nano - end.nano)
            millis should { (10 * 1e6 - 1e4 <= it) and (it <= 10 * 1e6 + 1e4) }
        }
    }
})
